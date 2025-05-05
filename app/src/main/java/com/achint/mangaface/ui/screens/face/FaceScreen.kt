@file:OptIn(ExperimentalMaterial3Api::class)

package com.achint.mangaface.ui.screens.face

import android.content.Context
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.achint.mangaface.ui.components.Toolbar
import com.achint.mangaface.utils.FaceDetectorHelper
import com.achint.mangaface.utils.PermissionManager
import java.util.concurrent.Executors

@Composable
fun Overlay(faceBounds: RectF?) {
    Log.d("MYDEBUG", "face $faceBounds")
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Define bounding box in center 40% area
        val boxWidth = canvasWidth * 0.8f
        val boxHeight = canvasHeight * 0.6f
        val boxLeft = (canvasWidth - boxWidth) / 2
        val boxTop = (canvasHeight - boxHeight) / 2

        val boundingBox = RectF(
            boxLeft,
            boxTop,
            boxLeft + boxWidth,
            boxTop + boxHeight
        )
        Log.d("MYDEBUG", "box $boundingBox")
        if (faceBounds == null){
            drawRect(
                color = Color.Red,
                topLeft = Offset(boundingBox.left, boundingBox.top),
                size = Size(boundingBox.width(), boundingBox.height()),
                style = Stroke(width = 3f)
            )
            return@Canvas
        }

        val isInside = boundingBox.left <= faceBounds.left &&
                boundingBox.top <= faceBounds.top &&
                boundingBox.right >= faceBounds.right &&
                boundingBox.bottom >= faceBounds.bottom

        val color = if (isInside) Color.Green else Color.Red

        drawRect(
            color = color,
            topLeft = Offset(boundingBox.left, boundingBox.top),
            size = Size(boundingBox.width(), boundingBox.height()),
            style = Stroke(width = 3f)
        )

        drawRect(
            color = Color.Blue,
            topLeft = Offset(faceBounds.left, faceBounds.top),
            size = Size(faceBounds.width(), faceBounds.height()),
            style = Stroke(width = 10f)
        )

    }

}

fun createFaceDetector(
    context: Context,
    onResults: (FaceDetectorHelper.ResultBundle) -> Unit
): FaceDetectorHelper {
    return FaceDetectorHelper(
        context = context,
        faceDetectorListener = object : FaceDetectorHelper.DetectorListener {
            override fun onError(error: String, errorCode: Int) {
                Log.d("MYDEBUG", "$error")
            }

            override fun onResults(resultBundle: FaceDetectorHelper.ResultBundle) {
                Log.d("FaceDetector", " on results${resultBundle.results}")
                onResults(resultBundle)
            }

        })
}


@Composable
fun FaceScreenRoot(modifier: Modifier = Modifier) {
    FaceScreen()
}

@Composable
fun FaceScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var hasCameraPermission by remember {
        mutableStateOf(PermissionManager.hasCameraPermission(context))
    }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasCameraPermission = isGranted
        }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(PermissionManager.CAMERA_PERMISSION)
        }
    }
    var faceBounds = remember { mutableStateOf<RectF?>(null) }
    val faceDetector = remember {
        createFaceDetector(context) {
            Log.d("MYDEBUG", "$it")
            if (it.results[0].detections().isEmpty()) {
                faceBounds.value = null
                return@createFaceDetector
            }
            for (detection in it.results[0].detections()) {
                val box = detection.boundingBox()

                val imageWidth = 480f
                val imageHeight = 640f

                val canvasWidth = context.resources.displayMetrics.widthPixels.toFloat()
                val canvasHeight = context.resources.displayMetrics.heightPixels.toFloat()

                // No flipping
                val mappedLeft = box.left / imageWidth * canvasWidth
                val mappedRight = box.right / imageWidth * canvasWidth

                val mappedTop = box.top / imageHeight * canvasHeight
                val mappedBottom = box.bottom / imageHeight * canvasHeight

                faceBounds.value = RectF(
                    mappedLeft.toFloat(),
                    mappedTop,
                    mappedRight,
                    mappedBottom
                )
            }


        }
    }
    Scaffold(
        topBar = {
            Toolbar(title = "Face Recognition")
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    innerPadding
                )
        ) {
            if (hasCameraPermission) {
                val controller = remember {
                    LifecycleCameraController(context)
                }
                CameraPreview(faceDetector)
                Overlay(faceBounds.value)
            }
        }
    }

}

@androidx.annotation.OptIn(ExperimentalGetImage::class)
@Composable
fun CameraPreview(faceDetector: FaceDetectorHelper) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current


    AndroidView(
        factory = { androidContext ->
            val previewView = PreviewView(androidContext)

            val cameraProviderFuture = ProcessCameraProvider.getInstance(androidContext)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = androidx.camera.core.Preview.Builder()
                    .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                    .build().also {
                        it.surfaceProvider = previewView.surfaceProvider
                    }

//                val analysis = ImageAnalysis.Builder()
//                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                    .build()
                val backgroundExecutor = Executors.newSingleThreadExecutor()

                val imageAnalyzer =
                    ImageAnalysis.Builder()
                        .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .setOutputImageFormat(OUTPUT_IMAGE_FORMAT_RGBA_8888)
                        .build()
                        // The analyzer can then be assigned to the instance
                        .also {
                            it.setAnalyzer(
                                backgroundExecutor,
                                faceDetector::detectLivestreamFrame
                            )
                        }

//                analysis.setAnalyzer(ContextCompat.getMainExecutor(androidContext)) { imageProxy ->
//                    val image = imageProxy.image
//
//                    val bitmapBuffer =
//                        Bitmap.createBitmap(
//                            imageProxy.width,
//                            imageProxy.height,
//                            Bitmap.Config.ARGB_8888
//                        )
////                    imageProxy.use { bitmapBuffer.copyPixelsFromBuffer(imageProxy.planes[0].buffer) }
////                    imageProxy.close()
//                    val matrix = Matrix().apply {
//                        // Rotate the frame received from the camera to be in the same direction as it'll be shown
//                        postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
//
//                        // flip image if user use front camera
//
//                        postScale(
//                            -1f,
//                            1f,
//                            imageProxy.width.toFloat(),
//                            imageProxy.height.toFloat()
//                        )
//
//                    }
//                    val rotatedBitmap = Bitmap.createBitmap(
//                        bitmapBuffer, 0, 0, bitmapBuffer.width, bitmapBuffer.height,
//                        matrix, true
//                    )
//                    val mpImage = BitmapImageBuilder(rotatedBitmap).build()
//
//                     faceDetector.detectLivestreamFrame(imageProxy)
//                }

                val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalyzer
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }, ContextCompat.getMainExecutor(androidContext))

            previewView
        }
    )
}


@Preview
@Composable
fun FaceScreenPrev() {
    FaceScreen()
}