package com.achint.mangaface.ui.screens.manga

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.achint.mangaface.domain.model.MangaModel
import com.achint.mangaface.ui.theme.Background
import com.achint.mangaface.utils.mangaErrorMessages

@Composable
fun MangaScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: MangaViewModel = hiltViewModel(),
    navigateToMangaDetail: (MangaModel) -> Unit = {}
) {
    MangaScreen(
        mangas = viewModel.mangaFlow.collectAsLazyPagingItems(),
        navigateToMangaDetail = navigateToMangaDetail
    )

}

@Composable
fun MangaScreen(
    modifier: Modifier = Modifier,
    mangas: LazyPagingItems<MangaModel>,
    navigateToMangaDetail: (MangaModel) -> Unit
) {

    val context = LocalContext.current
    LaunchedEffect(mangas.loadState) {
        if (mangas.loadState.refresh is LoadState.Error) {
            val error = (mangas.loadState.refresh as LoadState.Error).error
            Toast.makeText(context, mangaErrorMessages(error), Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        if (mangas.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3), contentPadding = PaddingValues(4.dp)
            ) {
                items(mangas.itemCount) { index ->
                    val manga = mangas[index]
                    if (manga != null) {
                        MangaGridItem(onClick = {
                            navigateToMangaDetail(
                                manga
                            )
                        }, mangaModel = manga)
                    }
                }
                item {
                    Box(contentAlignment = Alignment.Center) {
                        if (mangas.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp),
                                color = Color.White
                            )
                        }
                    }

                }
            }
        }
    }
}


