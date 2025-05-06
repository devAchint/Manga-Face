package com.achint.mangaface.data.mappers

import com.achint.mangaface.data.local.MangaEntity
import com.achint.mangaface.data.remote.MangaResponse
import com.achint.mangaface.domain.model.MangaModel

fun MangaResponse.asMangaEntity(id:Long): MangaEntity {
    return MangaEntity(
        title = title,
        total_chapter = total_chapter,
        thumb = thumb,
        type = type,
        sub_title = sub_title,
        status = status,
        summary = summary,
        nsfw = nsfw,
        genres = genres,
        authors = authors,
        id=id

    )
}

fun MangaEntity.asManga(): MangaModel {
    return MangaModel(
        id = id,
        title = title,
        total_chapter = total_chapter,
        thumb = thumb,
        type = type,
        sub_title = sub_title,
        status = status,
        summary = summary,
        nsfw = nsfw,
        genres = genres,
        authors = authors,
    )
}