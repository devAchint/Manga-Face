package com.achint.mangaface.utils


fun String.isValidEmail(): Boolean {
    return isNotBlank() && contains("@") && contains(".")
}

fun String.isValidPassword(): Boolean {
    return isNotBlank() && length >= 6
}