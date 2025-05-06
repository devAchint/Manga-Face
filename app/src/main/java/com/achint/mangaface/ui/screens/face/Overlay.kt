package com.achint.mangaface.ui.screens.face

import android.graphics.RectF
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.google.mediapipe.tasks.vision.facedetector.FaceDetectorResult
import kotlin.math.min

@Composable
fun Overlay(
    detectionResults: FaceDetectorResult,
    imageHeight: Int,
    imageWidth: Int
) {
    //Log.d("MYDEBUG", "face $faceBounds")
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        Log.d("MYDEBUG", "width height $canvasHeight $canvasWidth ")
        val boxWidth = canvasWidth * 0.8f
        val boxHeight = canvasHeight * 0.6f
        val boxLeft = (canvasWidth - boxWidth) / 2
        val boxTop = (canvasHeight - boxHeight) / 2

        val scaleFactor = min(canvasWidth * 1f / imageWidth, canvasHeight * 1f / imageHeight)

        // Define reference box in center 40% area


        val referenceBox = RectF(
            boxLeft,
            boxTop,
            boxLeft + boxWidth,
            boxTop + boxHeight
        )

        for (detection in detectionResults.detections()) {
            val boundingBox = detection.boundingBox()

            val top = boundingBox.top * scaleFactor
            val bottom = boundingBox.bottom * scaleFactor
            val left = boundingBox.left * scaleFactor
            val right = boundingBox.right * scaleFactor

            val drawableRect = RectF(left, top, right, bottom)
            drawRect(
                color = Color.Blue,
                topLeft = Offset(drawableRect.left, drawableRect.top),
                size = Size(drawableRect.width(), drawableRect.height()),
                style = Stroke(width = 3f)
            )

            Log.d("MYDEBUG", "box $referenceBox")
//        if (faceBounds == null) {
//            drawRect(
//                color = Color.Red,
//                topLeft = Offset(referenceBox.left, referenceBox.top),
//                size = Size(referenceBox.width(), referenceBox.height()),
//                style = Stroke(width = 3f)
//            )
//            return@Canvas
//        }

            val isInside = referenceBox.left <= drawableRect.left &&
                    referenceBox.top <= drawableRect.top &&
                    referenceBox.right >= drawableRect.right &&
                    referenceBox.bottom >= drawableRect.bottom

            val color = if (isInside) Color.Green else Color.Red

            drawRect(
                color = color,
                topLeft = Offset(referenceBox.left, referenceBox.top),
                size = Size(referenceBox.width(), referenceBox.height()),
                style = Stroke(width = 3f)
            )

//            drawRect(
//                color = Color.Blue,
//                topLeft = Offset(drawableRect.left, drawableRect.top),
//                size = Size(drawableRect.width(), drawableRect.height()),
//                style = Stroke(width = 10f)
//            )
        }

    }

}