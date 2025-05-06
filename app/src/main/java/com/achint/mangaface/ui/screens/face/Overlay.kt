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


