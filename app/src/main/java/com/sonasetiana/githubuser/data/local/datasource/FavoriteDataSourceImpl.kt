package com.sonasetiana.githubuser.data.local.datasource

import com.sonasetiana.githubuser.data.local.room.dao.FavoriteDao
import com.sonasetiana.githubuser.data.model.FavoriteData
import kotlinx.coroutines.flow.Flow

class FavoriteDataSourceImpl(
    private val dao: FavoriteDao
) : FavoriteDataSource{
    override suspend fun getFavorites(): Flow<List<FavoriteData>> = dao.getFavorites()

    override suspend fun insert(values: FavoriteData) = dao.insert(values)

    override suspend fun update(values: FavoriteData) = dao.update(values)

    override suspend fun delete(values: FavoriteData) = dao.delete(values)
}