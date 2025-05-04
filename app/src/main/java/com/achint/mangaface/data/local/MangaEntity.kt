package com.achint.mangaface.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga")
data class MangaEntity(
    @PrimaryKey
    val id: String,
    val create_at: Long,
    val nsfw: Boolean,
    val status: String,
    val sub_title: String,
    val summary: String,
    val thumb: String,
    val title: String,
    val total_chapter: Int,
    val type: String,
    val update_at: Long
)