package com.achint.mangaface.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MangaDao {

    @Upsert
    suspend fun upsertAll(manga: List<MangaEntity>)

    @Query("SELECT * FROM manga")
    fun pagingSource(): PagingSource<Int, MangaEntity>

    @Query("Delete From manga")
    suspend fun clearAll()

    @Query("SELECT * FROM manga ORDER BY id DESC LIMIT 1")
    suspend fun getLastManga(): MangaEntity?
}