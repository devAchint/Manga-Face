package com.achint.mangaface.ui.navigation

import com.achint.mangaface.domain.model.MangaModel
import kotlinx.serialization.Serializable

@Serializable
object SignIn

@Serializable
object Manga

@Serializable
data class MangaDetail(val mangaModel: MangaModel)

@Serializable
object Face