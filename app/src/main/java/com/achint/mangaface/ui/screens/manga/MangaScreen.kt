package com.achint.mangaface.ui.screens.manga

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.achint.mangaface.R
import com.achint.mangaface.domain.model.MangaModel

@Composable
fun MangaScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: MangaViewModel = hiltViewModel(),
    navigateToMangaDetail: (String) -> Unit = {}
) {
    MangaScreen(
        mangas = viewModel.mangaFlow.collectAsLazyPagingItems(),
        navigateToMangaDetail = navigateToMangaDetail
    )
//    val imageUrl =
//        "https://usc1.contabostorage.com/scraper/mangas/65a52ea8f64a55128b487e1b/thumb.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=c10e9464b360c31ce8abea9b266076f6%2F20250505%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250505T144447Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&X-Amz-Signature=b715d999ff13d8d3b677e33cf415c5d34188828a6386a0767f5c8b41909c0675"
//
//    AsyncImage(
//        model = ImageRequest.Builder(LocalContext.current)
//            .data(imageUrl)
//            .crossfade(true)
//            .listener(
//                onError = { request, result ->
//                    Log.e("MYDEBUG", "Image load failed: ${result.throwable.message}")
//                },
//                onSuccess = { request, metadata ->
//                    Log.d("MYDEBUG", "Image load succeeded")
//                }
//            )
//            .build(),
//
//        contentDescription = null,
//        contentScale = ContentScale.Crop,
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(200.dp)
//            .border(1.dp, Color.Black)
//    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaScreen(
    modifier: Modifier = Modifier,
    mangas: LazyPagingItems<MangaModel>,
    navigateToMangaDetail: (String) -> Unit
) {

    val context = LocalContext.current
    LaunchedEffect(mangas.loadState) {
        if (mangas.loadState.refresh is LoadState.Error) {
            val error = (mangas.loadState.refresh as LoadState.Error).error
            Toast.makeText(context, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (mangas.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(mangas.itemCount) { index ->
                    val manga = mangas[index]
                    if (manga != null) {
                        MangaGridItem(onClick = {
                            navigateToMangaDetail(
                                manga.id
                            )
                        }, mangaModel = manga)
                    }
                }
                item {
                    if (mangas.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

}


@Composable
fun MangaGridItem(modifier: Modifier = Modifier, mangaModel: MangaModel, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick()
            }
    ) {
        Image(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data("https://usc1.contabostorage.com/scraper/mangas/65a52ea8f64a55128b487e1b/thumb.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=c10e9464b360c31ce8abea9b266076f6%2F20250504%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250504T064706Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&X-Amz-Signature=02ea8e67084a9ffe8b4c205804be28db429b8bb5456846848ae416b2cdce3ff9") // full URL
//                .crossfade(true)
//                .listener(
//                    onError = { request, result ->
//                        Log.e("MYDEBUG", "Image load failed: ${result.throwable.message}")
//                    },
//                    onSuccess = { request, metadata ->
//                        Log.d("MYDEBUG", "Image load succeeded")
//                    }
//                )
//                .build(),
            painter = painterResource(R.drawable.thumb),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Text(text = mangaModel.title.trim(), maxLines = 2, overflow = TextOverflow.Ellipsis)
    }
}

@Preview
@Composable
fun MangaScreenPreview() {
    // MangaScreen()
}