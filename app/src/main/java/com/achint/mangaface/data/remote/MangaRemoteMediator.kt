package com.achint.mangaface.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.achint.mangaface.data.local.MangaEntity
import com.achint.mangaface.data.local.MyAppDatabase
import com.achint.mangaface.data.mappers.asMangaEntity

@OptIn(ExperimentalPagingApi::class)
class MangaRemoteMediator(
    private val db: MyAppDatabase,
    private val apiInterface: ApiInterface
) : RemoteMediator<Int, MangaEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MangaEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    100
                }
            }
            val response = apiInterface.fetchManga(loadKey)
            if (!response.isSuccessful) {
                return MediatorResult.Error(retrofit2.HttpException(response))
            }
            val mangaList = response.body()?.mangaList ?: emptyList()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.mangaDao().clearAll()
                }
                val mangaEntities = mangaList.map { it.asMangaEntity() }
                db.mangaDao().upsertAll(mangaEntities)
            }
            MediatorResult.Success(
                endOfPaginationReached = mangaList.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}