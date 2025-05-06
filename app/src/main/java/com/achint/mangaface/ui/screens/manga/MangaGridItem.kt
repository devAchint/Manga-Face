package com.achint.mangaface.ui.screens.manga

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.achint.mangaface.domain.model.MangaModel
import com.achint.mangaface.ui.theme.LightTextColor
import com.achint.mangaface.ui.theme.nunFontFamily

@Composable
fun MangaGridItem(modifier: Modifier = Modifier, mangaModel: MangaModel, onClick: () -> Unit) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable {
            onClick()
        }) {
        AsyncImage(
            model = mangaModel.thumb,
            contentDescription = mangaModel.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(LightTextColor)
                .height(200.dp)
        )

        Text(
            text = mangaModel.title.trim(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontFamily = nunFontFamily,
            fontWeight = FontWeight.Medium
        )
    }
}