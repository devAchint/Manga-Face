package com.achint.mangaface.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.achint.mangaface.data.local.UserDao
import com.achint.mangaface.data.local.UserEntity
import com.achint.mangaface.domain.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val dataStore: DataStore<Preferences>
) : UsersRepository {

    companion object {
        val IS_USER_SIGNED_IN = booleanPreferencesKey("IS_USER_SIGNED_IN")
    }

    override suspend fun signIn(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val user = userDao.getUser(email)
            val isValidPassword = user.password == password
            if (isValidPassword){
                saveUserSignedIn(true)
            }
            isValidPassword
        }
    }

    override suspend fun isUserAlreadyExists(email: String): Boolean {
        return withContext(Dispatchers.IO) {
            userDao.isUserAlreadyExists(email)
        }
    }

    override suspend fun signUp(email: String, password: String) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(UserEntity(email = email, password = password))
            saveUserSignedIn(true)
        }
    }

    override suspend fun isUserSignedIn(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[IS_USER_SIGNED_IN] ?: false
    }


    private suspend fun saveUserSignedIn(userSignedIn: Boolean) {
        try {
            dataStore.edit { preferences ->
                preferences[IS_USER_SIGNED_IN] = userSignedIn
            }
        } catch (e: Exception) {
            Log.d("MYDEBUG", "${e.message}")
        }
    }
}