package com.achint.mangaface.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("manga/fetch")
    suspend fun fetchManga(): Response<FetchMangaResponse>
}