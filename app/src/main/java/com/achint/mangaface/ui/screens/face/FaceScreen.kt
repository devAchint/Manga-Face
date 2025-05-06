package com.achint.mangaface.ui.screens.face

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.achint.mangaface.ui.components.LoadingButton
import com.achint.mangaface.ui.theme.nunFontFamily
import com.achint.mangaface.utils.FaceDetectorHelper
import com.achint.mangaface.utils.PermissionManager
import com.achint.mangaface.utils.isFaceInsideReferenceBox


fun createFaceDetector(
    context: Context,
    onResults: (FaceDetectorHelper.ResultBundle) -> Unit
): FaceDetectorHelper {
    return FaceDetectorHelper(
        context = context,
        faceDetectorListener = object : FaceDetectorHelper.DetectorListener {
            override fun onError(error: String, errorCode: Int) {
                Log.d("MYDEBUG", error)
            }

            override fun onResults(resultBundle: FaceDetectorHelper.ResultBundle) {
                Log.d("FaceDetector", " on results${resultBundle.results}")
                onResults(resultBundle)
            }
        }
    )
}

@Composable
fun FaceScreenRoot(modifier: Modifier = Modifier) {
    FaceScreen()
}

@Composable
fun FaceScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val activity = context as? Activity
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember {
        mutableStateOf(PermissionManager.hasCameraPermission(context))
    }
    var isRationale by remember {
        mutableStateOf(false)
    }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                hasCameraPermission = true
            } else if (activity != null &&
                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    PermissionManager.CAMERA_PERMISSION
                )
            ) {
                isRationale = true
            } else {
                hasCameraPermission = false
            }
        }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(PermissionManager.CAMERA_PERMISSION)
        }
    }

    var isFaceInside by remember {
        mutableStateOf(false)
    }



    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val canvasWidth = constraints.maxWidth.toFloat()
        val canvasHeight = constraints.maxHeight.toFloat()


        val faceDetector by remember {
            mutableStateOf(createFaceDetector(context) { rB ->
                val detectionResult = rB.results[0]
                val imageWidth = rB.inputImageWidth
                val imageHeight = rB.inputImageHeight
                isFaceInside = isFaceInsideReferenceBox(
                    detectionResult = detectionResult,
                    imageWidth = imageWidth,
                    imageHeight = imageHeight,
                    canvasWidth = canvasWidth,
                    canvasHeight = canvasHeight
                )
            })
        }

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {
                        faceDetector.setupFaceDetector()
                    }

                    Lifecycle.Event.ON_PAUSE -> {
                        faceDetector.clearFaceDetector()
                    }

                    else -> {}
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
                faceDetector.clearFaceDetector()
            }
        }

        if (hasCameraPermission) {
            CameraPreview {
                faceDetector.detectLivestreamFrame(it)
            }

            Overlay(
                isInside = isFaceInside,
                canvasWidth = canvasWidth,
                canvasHeight = canvasHeight
            )
        } else {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Permission Required",
                    fontSize = 24.sp,
                    fontFamily = nunFontFamily,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "To access face detection, please grant camera permission.",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                LoadingButton(
                    text = "Grant",
                    onClick = {
                        if (isRationale) {
                            permissionLauncher.launch(PermissionManager.CAMERA_PERMISSION)
                        } else {
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null)
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        }
                    }
                )
            }

        }
    }

}


