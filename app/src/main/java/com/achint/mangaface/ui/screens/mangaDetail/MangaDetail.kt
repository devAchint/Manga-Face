package com.achint.mangaface.ui.screens.mangaDetail

import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.achint.mangaface.R
import com.achint.mangaface.domain.model.MangaModel
import com.achint.mangaface.ui.components.LoadingButton
import com.achint.mangaface.ui.theme.LightTextColor
import com.achint.mangaface.ui.theme.PrimaryColor
import com.achint.mangaface.ui.theme.nunFontFamily
import com.achint.mangaface.utils.toTitleCase
import java.util.Locale

@Composable
fun MangaDetailScreenRoot(modifier: Modifier = Modifier) {
    val manga = MangaModel(
        id = "65a52ea8f64a55128b487e1b",
        title = " A World of Gold to You",
        sub_title = "",
        status = "ongoing",
        thumb = "https://usc1.contabostorage.com/scraper/mangas/65a52ea8f64a55128b487e1b/thumb.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=c10e9464b360c31ce8abea9b266076f6%2F20250505%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250505T144447Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&X-Amz-Signature=b715d999ff13d8d3b677e33cf415c5d34188828a6386a0767f5c8b41909c0675",
        summary = "Once upon a time, the world had been divided into the Moon Kingdom, inhabited by humans, and the Sun Kingdom, inhabited by demons. Estelle, a knight of the Moon Kingdom, loses her family to an ambush from the demons and is sentenced to life in exile after becoming entangled with a mysterious demon child. To survive, she sets out to a mysterious tower in the north with her companions. A timid knight, a slave boy, and a shadow who was once the Demon King. This is an alluring story about three people embarking on an adventure to find their true selves.\n\n",
        authors = listOf(""),
        genres = listOf("Action", "Fantasy", "Manga", "Adventure", "Seinen", "Manhwa", "Mature"),
        nsfw = true,
        type = "korea",
        total_chapter = 0,
        create_at = 1705324200839,
        update_at = 1705324333619
    )

    MangaDetailScreen(manga = manga)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaDetailScreen(
    modifier: Modifier = Modifier, manga: MangaModel
) {
    Scaffold(
    ) { innerPadding ->
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(
                    R.drawable.thumb
                ), contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            )
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
                                text = it, modifier = Modifier
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
}
