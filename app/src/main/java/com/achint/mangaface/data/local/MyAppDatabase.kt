package com.achint.mangaface.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
abstract class MyAppDatabase : RoomDatabase() {

    abstract fun usersDao(): UserDao
}



