package com.achint.mangaface.utils

import android.graphics.RectF
import com.google.mediapipe.tasks.vision.facedetector.FaceDetectorResult
import java.util.Locale
import kotlin.math.min


fun String.isValidEmail(): Boolean {
    return isNotBlank() && contains("@") && contains(".")
}

fun String.isValidPassword(): Boolean {
    return isNotBlank() && length >= 6
}

fun String.toTitleCase(): String {
    return replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
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


fun mangaErrorMessages(error: Throwable): String {
    return when (error) {
        is java.net.UnknownHostException -> "No internet connection."
        is retrofit2.HttpException -> {
            when (error.code()) {
                429 -> "You have exceeded the DAILY quota for Requests on your current plan."
                500 -> "Server error. Please try again."
                404 -> "Content not found."
                else -> "Server error: ${error.code()}"
            }
        }

        is java.net.SocketTimeoutException -> "Request timed out. Please try again."
        else -> "Unexpected error: ${error.localizedMessage}"
    }
}