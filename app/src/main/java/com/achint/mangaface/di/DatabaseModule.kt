package com.achint.mangaface.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.achint.mangaface.data.local.MangaEntity
import com.achint.mangaface.data.local.MyAppDatabase
import com.achint.mangaface.data.local.UserDao
import com.achint.mangaface.data.remote.ApiInterface
import com.achint.mangaface.data.remote.MangaRemoteMediator
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


    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { appContext.preferencesDataStoreFile("") }
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideMangaPager(db:MyAppDatabase, mangaApi:ApiInterface): Pager<Int, MangaEntity> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            remoteMediator = MangaRemoteMediator(
                db = db,
                apiInterface = mangaApi
            ),
            pagingSourceFactory = {
                db.mangaDao().pagingSource()
            }
        )
    }

}

