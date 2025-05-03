package com.achint.mangaface.di

import android.content.Context
import androidx.room.Room
import com.achint.mangaface.data.local.MyAppDatabase
import com.achint.mangaface.data.local.UserDao
import com.achint.mangaface.data.repository.UsersRepositoryImpl
import com.achint.mangaface.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDb(@ApplicationContext context: Context): MyAppDatabase {
        return Room.databaseBuilder(context, MyAppDatabase::class.java, "myAppDb").build()
    }

    @Provides
    fun providesUsersDao(db: MyAppDatabase): UserDao {
        return db.usersDao()
    }

}

