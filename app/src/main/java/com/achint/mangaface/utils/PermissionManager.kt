package com.achint.mangaface.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat


object PermissionManager {
    const val CAMERA_PERMISSION = android.Manifest.permission.CAMERA

    fun hasCameraPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            CAMERA_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    }
}