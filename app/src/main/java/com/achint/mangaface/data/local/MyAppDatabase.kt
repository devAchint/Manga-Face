package com.achint.mangaface.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.achint.mangaface.ui.navigation.Manga

@Database(entities = [UserEntity::class,MangaEntity::class], version = 1)
abstract class MyAppDatabase : RoomDatabase() {

    abstract fun usersDao(): UserDao
    abstract fun mangaDao(): MangaDao
}



