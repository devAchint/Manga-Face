package com.achint.mangaface.ui.screens.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.achint.mangaface.data.local.MangaEntity
import com.achint.mangaface.data.mappers.asManga
import com.achint.mangaface.domain.model.MangaModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MangaUiState(
    val isLoading: Boolean = false,
    val mangas: PagingData<MangaModel> = PagingData.empty()
)

@HiltViewModel
class MangaViewModel @Inject constructor(pager: Pager<Int, MangaEntity>) :
    ViewModel() {

    private val _mangaUiState = MutableStateFlow<MangaUiState>(MangaUiState())
    val mangaUiState = _mangaUiState.asStateFlow()

    val mangaFlow = pager.flow.map { pagingData ->
        pagingData.map { it.asManga() }
    }.cachedIn(viewModelScope)

    init {
       // fetchManga()
    }

    private fun fetchManga() {
        viewModelScope.launch {
            _mangaUiState.update { it.copy(isLoading = true) }
            mangaFlow.collectLatest { pagingData ->
                _mangaUiState.update { it.copy(mangas = pagingData, isLoading = false) }
            }
        }
    }
}