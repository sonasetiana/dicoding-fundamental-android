package com.sonasetiana.githubuser.data.local.datasource

import com.sonasetiana.githubuser.data.local.room.BaseResults
import com.sonasetiana.githubuser.data.model.FavoriteData
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getFavorites() : Flow<BaseResults<List<FavoriteData>>>
    suspend fun findFavorites(userId: Int) : Flow<BaseResults<List<FavoriteData>>>
    suspend fun insert(values: FavoriteData)
    suspend fun update(values: FavoriteData)
    suspend fun delete(values: FavoriteData)
}