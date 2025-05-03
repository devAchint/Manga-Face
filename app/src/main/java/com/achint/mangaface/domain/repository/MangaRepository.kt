package com.achint.mangaface.domain.repository

import com.achint.mangaface.domain.model.MangaModel

interface MangaRepository {
   suspend fun fetchManga(): List<MangaModel>
}