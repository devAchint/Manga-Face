package com.achint.mangaface.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MangaModel(
    val authors: List<String>,
    val genres: List<String>,
    val id: Long,
    val nsfw: Boolean,
    val status: String,
    val sub_title: String,
    val summary: String,
    val thumb: String,
    val title: String,
    val total_chapter: Int,
    val type: String,
)