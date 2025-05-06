@file:OptIn(ExperimentalMaterial3Api::class)

package com.achint.mangaface.ui.screens.face

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.achint.mangaface.utils.FaceDetectorHelper
import com.achint.mangaface.utils.PermissionManager
import java.util.concurrent.Executors


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
    var resultBundle = remember { mutableStateOf<FaceDetectorHelper.ResultBundle?>(null) }
    val faceDetector = remember {
        createFaceDetector(context) {
            resultBundle.value = it
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (hasCameraPermission) {
            CameraPreview(faceDetector)

            resultBundle.value?.let {
                val detectionResult = it.results[0]

                Overlay(
                    detectionResults = detectionResult,
                    imageWidth = it.inputImageWidth,
                    imageHeight = it.inputImageHeight,
                )
            }


        }
    }

}



@Composable
fun CameraPreview(faceDetector: FaceDetectorHelper) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current


    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { androidContext ->
            val previewView = PreviewView(androidContext)
            previewView.scaleType = PreviewView.ScaleType.FIT_START

            val cameraProviderFuture = ProcessCameraProvider.getInstance(androidContext)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = androidx.camera.core.Preview.Builder()
                    .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                    .build().also {
                        it.surfaceProvider = previewView.surfaceProvider
                    }


                val backgroundExecutor = Executors.newSingleThreadExecutor()

                val imageAnalyzer =
                    ImageAnalysis.Builder()
                        .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                        .setTargetRotation(previewView.display.rotation)
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

                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_FRONT).build()

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