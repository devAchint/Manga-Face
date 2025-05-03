package com.achint.mangaface.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT Exists(SELECT 1 FROM users WHERE email = :email)")
   suspend fun isUserAlreadyExists(email: String): Boolean

    @Query("SELECT * FROM users WHERE email = :email")
   suspend fun getUser(email:String):UserEntity
}