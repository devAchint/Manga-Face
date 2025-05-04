package com.achint.mangaface.domain.repository


interface UsersRepository {
    suspend fun signUp(email: String, password: String)

    suspend fun signIn(email: String, password: String): Boolean

    suspend fun isUserAlreadyExists(email: String): Boolean

    suspend fun isUserSignedIn(): Boolean
}
