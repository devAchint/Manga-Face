package com.achint.mangaface.ui.screens.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.achint.mangaface.data.local.MangaEntity
import com.achint.mangaface.data.mappers.asManga
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class MangaViewModel @Inject constructor(pager: Pager<Int, MangaEntity>) :
    ViewModel() {

    val mangaFlow = pager.flow.map { pagingData ->
        pagingData.map { it.asManga() }
    }.cachedIn(viewModelScope)

}