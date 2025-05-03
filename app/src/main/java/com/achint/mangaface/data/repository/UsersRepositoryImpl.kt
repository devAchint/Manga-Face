package com.achint.mangaface.data.repository

import com.achint.mangaface.data.local.UserDao
import com.achint.mangaface.data.local.UserEntity
import com.achint.mangaface.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(private val userDao: UserDao) : UsersRepository {

    override suspend fun signIn(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO){
            val user = userDao.getUser(email)
            user.password == password
        }
    }

    override suspend fun isUserAlreadyExists(email: String): Boolean {
        return withContext(Dispatchers.IO){
            userDao.isUserAlreadyExists(email)
        }
    }

    override suspend fun signUp(userEntity: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(userEntity)
        }
    }
}