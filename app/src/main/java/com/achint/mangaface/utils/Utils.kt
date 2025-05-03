package com.achint.mangaface.utils

import com.achint.mangaface.data.remote.MangaResponse
import com.achint.mangaface.domain.model.MangaModel

fun MangaResponse.asMangaModel():MangaModel{
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
        genres = genres,
        authors = authors,
        create_at = create_at
    )
}