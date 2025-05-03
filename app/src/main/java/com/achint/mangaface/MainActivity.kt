package com.achint.mangaface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.achint.mangaface.ui.navigation.AppNavHost
import com.achint.mangaface.ui.theme.MangaFaceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MangaFaceTheme(darkTheme = false) {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}


