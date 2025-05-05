package com.achint.mangaface.utils

import java.util.Locale


fun String.isValidEmail(): Boolean {
    return isNotBlank() && contains("@") && contains(".")
}

fun String.isValidPassword(): Boolean {
    return isNotBlank() && length >= 6
}

fun String.toTitleCase():String{
    return replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }
}