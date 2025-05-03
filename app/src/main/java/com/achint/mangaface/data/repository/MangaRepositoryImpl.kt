package com.achint.mangaface.data.repository

import com.achint.mangaface.data.remote.ApiInterface
import com.achint.mangaface.domain.model.MangaModel
import com.achint.mangaface.domain.repository.MangaRepository
import com.achint.mangaface.utils.asMangaModel
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface) :
    MangaRepository {

    override suspend fun fetchManga(): List<MangaModel> {
        val response = apiInterface.fetchManga()
        return response.body()?.mangaList?.map { it.asMangaModel() } ?: emptyList()
    }
}