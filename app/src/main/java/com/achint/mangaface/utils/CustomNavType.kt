package com.achint.mangaface.utils

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.achint.mangaface.domain.model.MangaModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {
    val MangaType = object : NavType<MangaModel>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): MangaModel? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): MangaModel {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: MangaModel) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: MangaModel): String {
            return Uri.encode(Json.encodeToString(value))
        }

    }
}