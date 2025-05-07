package com.achint.mangaface.ui.screens.mangaDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.achint.mangaface.domain.model.MangaModel
import com.achint.mangaface.ui.theme.Background
import com.achint.mangaface.ui.theme.nunFontFamily

@Composable
fun MangaDetailScreenRoot(
    modifier: Modifier = Modifier,
    manga: MangaModel,
    onBackPressed: () -> Unit = {}
) {
    MangaDetailScreen(
        modifier = modifier,
        manga = manga,
        onBackPressed = onBackPressed
    )
}

@Composable
fun MangaDetailScreen(
    modifier: Modifier = Modifier,
    manga: MangaModel,
    onBackPressed: () -> Unit = {}
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(.8f)
                    .fillMaxHeight()
            ) {
                AsyncImage(
                    model = manga.thumb,
                    contentDescription = manga.title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()

                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = manga.title.trim(),
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = nunFontFamily,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    buildAnnotatedString {
                        manga.genres.forEachIndexed { index, item ->
                            append(item)
                            if (index != manga.genres.lastIndex) {
                                append(" • ")
                            }
                        }
                    },
                    color = Color.White
                )


            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = manga.summary,
            color = Color.White,
            fontFamily = nunFontFamily,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis
        )
    }

}
