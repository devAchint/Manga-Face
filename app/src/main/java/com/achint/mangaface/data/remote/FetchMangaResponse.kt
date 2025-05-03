package com.achint.mangaface.data.remote

import com.google.gson.annotations.SerializedName

data class FetchMangaResponse(
    val code: Int,
    @SerializedName("data")
    val mangaList: List<MangaResponse>
)