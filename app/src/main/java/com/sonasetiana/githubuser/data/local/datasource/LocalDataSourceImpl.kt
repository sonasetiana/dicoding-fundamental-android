package com.sonasetiana.githubuser.data.local.datasource

import com.sonasetiana.githubuser.data.local.room.BaseResults
import com.sonasetiana.githubuser.data.local.room.dao.FavoriteDao
import com.sonasetiana.githubuser.data.model.FavoriteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LocalDataSourceImpl(
    private val dao: FavoriteDao
) : LocalDataSource{
    override suspend fun getFavorites(): Flow<BaseResults<List<FavoriteData>>> = flow {
        try {
            dao.getFavorites().collect {
                emit(BaseResults(data = it))
            }
        }catch (e: Exception) {
            emit(BaseResults(error = e.localizedMessage.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun findFavorites(userId: Int): Flow<BaseResults<List<FavoriteData>>> = flow {
        try {
            dao.findFavorites(userId).collect {
                emit(BaseResults(data = it))
            }
        }catch (e: Exception) {
            emit(BaseResults(error = e.localizedMessage.orEmpty()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun insert(values: FavoriteData) = dao.insert(values)

    override suspend fun update(values: FavoriteData) = dao.update(values)

    override suspend fun delete(values: FavoriteData) = dao.delete(values)
}