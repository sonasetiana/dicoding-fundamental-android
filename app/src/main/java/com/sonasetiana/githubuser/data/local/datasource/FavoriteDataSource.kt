package com.sonasetiana.githubuser.data.local.datasource

import com.sonasetiana.githubuser.data.model.FavoriteData
import kotlinx.coroutines.flow.Flow

interface FavoriteDataSource {
    suspend fun getFavorites() : Flow<List<FavoriteData>>
    suspend fun insert(values: FavoriteData)
    suspend fun update(values: FavoriteData)
    suspend fun delete(values: FavoriteData)
}