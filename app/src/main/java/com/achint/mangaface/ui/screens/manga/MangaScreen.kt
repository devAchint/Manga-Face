package com.achint.mangaface.ui.screens.manga

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.achint.mangaface.domain.model.MangaModel

@Composable
fun MangaScreenRoot(modifier: Modifier = Modifier, viewModel: MangaViewModel = hiltViewModel()) {
    MangaScreen(mangaUiState = viewModel.mangaUiState.collectAsState().value)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaScreen(
    modifier: Modifier = Modifier, mangaUiState: MangaUiState = MangaUiState()
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("home") })
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(mangaUiState.mangas) { item ->
                    MangaGridItem(mangaModel = item)
                }
            }
        }
    }
}


@Composable
fun MangaGridItem(mangaModel: MangaModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = mangaModel.title)
    }
}

@Preview
@Composable
fun MangaScreenPreview() {
    MangaScreen()
}