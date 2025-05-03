package com.achint.mangaface.ui.screens.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achint.mangaface.domain.model.MangaModel
import com.achint.mangaface.domain.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MangaUiState(
    val isLoading: Boolean = false,
    val mangas: List<MangaModel> = emptyList()
)

@HiltViewModel
class MangaViewModel @Inject constructor(private val mangaRepository: MangaRepository) :
    ViewModel() {

    private val _mangaUiState = MutableStateFlow<MangaUiState>(MangaUiState())
    val mangaUiState = _mangaUiState.asStateFlow()

    init {
        fetchManga()
    }

    private fun fetchManga() {
        viewModelScope.launch {
            _mangaUiState.update { it.copy(isLoading = true) }
            val mangas = mangaRepository.fetchManga()
            _mangaUiState.update { it.copy(isLoading = false, mangas = mangas) }
        }
    }
}