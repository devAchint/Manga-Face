package com.achint.mangaface.data.remote


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("manga/fetch")
    suspend fun fetchManga(@Query("page") page: Int): Response<FetchMangaResponse>
}