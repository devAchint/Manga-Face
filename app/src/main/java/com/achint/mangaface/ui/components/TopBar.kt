package com.achint.mangaface.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(modifier: Modifier = Modifier, title: String) {
    TopAppBar(
        title = { Text(title, color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
    )
}