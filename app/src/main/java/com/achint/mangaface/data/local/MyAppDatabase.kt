package com.achint.mangaface.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserEntity::class, MangaEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MyAppDatabase : RoomDatabase() {

    abstract fun usersDao(): UserDao
    abstract fun mangaDao(): MangaDao
}



