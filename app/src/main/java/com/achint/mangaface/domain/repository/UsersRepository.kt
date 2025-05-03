package com.achint.mangaface.domain.repository

import com.achint.mangaface.data.local.UserEntity


interface UsersRepository {
    suspend fun signUp(userEntity: UserEntity)

    suspend fun signIn(email: String, password: String): Boolean

    suspend fun isUserAlreadyExists(email: String): Boolean
}
