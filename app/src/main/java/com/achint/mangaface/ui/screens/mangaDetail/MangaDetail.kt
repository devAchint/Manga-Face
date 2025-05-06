package com.achint.mangaface.ui.screens.mangaDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.achint.mangaface.domain.model.MangaModel
import com.achint.mangaface.ui.components.LoadingButton
import com.achint.mangaface.ui.theme.LightTextColor
import com.achint.mangaface.ui.theme.PrimaryColor
import com.achint.mangaface.ui.theme.nunFontFamily
import com.achint.mangaface.utils.toTitleCase

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

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            AsyncImage(
                model = manga.thumb,
                contentDescription = manga.title,
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxSize()
            )

            IconButton(
                onClick = onBackPressed,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 16.dp, start = 16.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .offset(y = (-16).dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
                .weight(1f)
                .padding(top = 20.dp, start = 20.dp, bottom = 12.dp, end = 20.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(PrimaryColor, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = manga.type.toTitleCase())
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow {
                    items(manga.genres) {
                        Text(
                            text = it,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    Color(0xfffafafa)
                                )
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = manga.title.trim(),
                    fontSize = 24.sp,
                    fontFamily = nunFontFamily,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Text(
                        text = "${manga.total_chapter} chapters",
                        fontFamily = nunFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = LightTextColor
                    )
                    val author =
                        manga.authors.firstOrNull()?.takeIf { it.isNotBlank() } ?: "Unknown"
                    Text(
                        text = "│ by $author",
                        fontFamily = nunFontFamily,
                        fontWeight = FontWeight.Medium
                    )

                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = manga.summary,
                    fontFamily = nunFontFamily,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Ellipsis
                )
            }
            LoadingButton(text = "Read Now")
        }
    }

}
