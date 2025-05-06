package com.achint.mangaface.ui.screens.face

import android.graphics.RectF
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.google.mediapipe.tasks.vision.facedetector.FaceDetectorResult
import kotlin.math.min

@Composable
fun Overlay(
    canvasWidth: Float,
    canvasHeight: Float,
    isInside: Boolean
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        Log.d("MYDEBUG", "width height $canvasHeight $canvasWidth ")
        val boxWidth = canvasWidth * 0.8f
        val boxHeight = canvasHeight * 0.6f
        val boxLeft = (canvasWidth - boxWidth) / 2
        val boxTop = (canvasHeight - boxHeight) / 2


        val referenceBox = RectF(
            boxLeft,
            boxTop,
            boxLeft + boxWidth,
            boxTop + boxHeight
        )

        val color = if (isInside) Color.Green else Color.Red

        drawRoundRect(
            color = color,
            topLeft = Offset(referenceBox.left, referenceBox.top),
            size = Size(referenceBox.width(), referenceBox.height()),
            style = Stroke(width = 10f),
            cornerRadius = CornerRadius(20f)
        )
    }

}


fun isFaceInsideReferenceBox(
    imageWidth: Int,
    imageHeight: Int,
    detectionResult: FaceDetectorResult,
    canvasWidth: Float,
    canvasHeight: Float
): Boolean {
    val scaleFactor =
        min(canvasWidth * 1f / imageWidth, canvasHeight * 1f / imageHeight)
    val boundingBox = detectionResult.detections().firstOrNull()?.boundingBox()

    val boxWidth = canvasWidth * 0.8f
    val boxHeight = canvasHeight * 0.6f
    val boxLeft = (canvasWidth - boxWidth) / 2
    val boxTop = (canvasHeight - boxHeight) / 2


    val referenceBox = RectF(
        boxLeft,
        boxTop,
        boxLeft + boxWidth,
        boxTop + boxHeight
    )

    val isInside = boundingBox?.let {
        val top = boundingBox.top * scaleFactor
        val bottom = boundingBox.bottom * scaleFactor
        val left = boundingBox.left * scaleFactor
        val right = boundingBox.right * scaleFactor

        val faceBox = RectF(left, top, right, bottom)

        referenceBox.left <= faceBox.left &&
                referenceBox.top <= faceBox.top &&
                referenceBox.right >= faceBox.right &&
                referenceBox.bottom >= faceBox.bottom

    } ?: false

    return isInside
}