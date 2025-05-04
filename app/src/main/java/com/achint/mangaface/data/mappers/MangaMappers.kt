package com.achint.mangaface.data.mappers

import com.achint.mangaface.data.local.MangaEntity
import com.achint.mangaface.data.remote.MangaResponse
import com.achint.mangaface.domain.model.MangaModel

fun MangaResponse.asMangaEntity(): MangaEntity {
    return MangaEntity(
        id = id,
        title = title,
        total_chapter = total_chapter,
        thumb = thumb,
        update_at = update_at,
        type = type,
        sub_title = sub_title,
        status = status,
        summary = summary,
        nsfw = nsfw,
        create_at = create_at
    )
}

fun MangaEntity.asManga(): MangaModel {
    return MangaModel(
        id = id,
        title = title,
        total_chapter = total_chapter,
        thumb = thumb,
        update_at = update_at,
        type = type,
        sub_title = sub_title,
        status = status,
        summary = summary,
        nsfw = nsfw,
        genres = listOf("Achint"),
        authors = listOf("Achint"),
        create_at = create_at
    )
}