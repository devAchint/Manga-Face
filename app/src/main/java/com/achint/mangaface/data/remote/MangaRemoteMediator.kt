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
                    val lastItem = state.lastItemOrNull()
                    if (lastItem==null){
                        1
                    }else{
                        val currentPage = (lastItem.id.toInt() / state.config.pageSize)
                        currentPage + 1
                    }
                }
            }
            val response = apiInterface.fetchManga(loadKey)
            if (!response.isSuccessful) {
                return MediatorResult.Error(retrofit2.HttpException(response))
            }
            val mangaList = response.body()?.mangaList ?: emptyList()
            db.withTransaction {
                var currentId = 1L
                if (loadType == LoadType.REFRESH) {
                    db.mangaDao().clearAll()
                } else {
                    currentId = (db.mangaDao().getLastManga()?.id ?: 0) + 1
                }
                val mangaEntities = mangaList.map { it.asMangaEntity(currentId++) }
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