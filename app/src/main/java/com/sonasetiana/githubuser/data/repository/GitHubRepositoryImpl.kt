package com.sonasetiana.githubuser.data.repository

import com.sonasetiana.githubuser.data.local.datasource.LocalDataSource
import com.sonasetiana.githubuser.data.local.room.RoomResults
import com.sonasetiana.githubuser.data.model.DetailUserData
import com.sonasetiana.githubuser.data.model.FavoriteData
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.data.remote.datasource.RemoteDataSource
import com.sonasetiana.githubuser.data.remote.network.NetworkResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class GitHubRepositoryImpl constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : BaseRepository(), GitHubRepository{

    override fun getAllUsers(): Flow<NetworkResults<List<UserData>>> = flow {
        emit(NetworkResults.Loading(true))
        remoteDataSource.getAllUsers().collect {
            if (it.data != null) {
                emit(NetworkResults.Success(it.data))
            }else {
                emit(NetworkResults.Error(it.error))
            }
        }
        emit(NetworkResults.Loading(false))
    }

    override fun searchUser(keyword: String): Flow<NetworkResults<List<UserData>>> = flow{
        emit(NetworkResults.Loading(true))
        remoteDataSource.searchUser(keyword).collect() {
            if (it.data != null) {
                emit(NetworkResults.Success(it.data))
            }else {
                emit(NetworkResults.Error(it.error))
            }
        }
        emit(NetworkResults.Loading(false))
    }

    override fun getDetailUser(username: String): Flow<NetworkResults<DetailUserData>> = flow{
        emit(NetworkResults.Loading(true))
        remoteDataSource.getDetailUser(username).collect {
            if (it.data != null) {
                emit(NetworkResults.Success(it.data))
            }else {
                emit(NetworkResults.Error(it.error))
            }
        }
        emit(NetworkResults.Loading(false))
    }

    override fun getFollowing(username: String): Flow<NetworkResults<List<UserData>>> = flow{
        emit(NetworkResults.Loading(true))
        remoteDataSource.getFollowing(username).collect {
            if (it.data != null) {
                emit(NetworkResults.Success(it.data))
            }else {
                emit(NetworkResults.Error(it.error))
            }
        }
        emit(NetworkResults.Loading(false))
    }

    override fun getFollower(username: String): Flow<NetworkResults<List<UserData>>> = flow{
        emit(NetworkResults.Loading(true))
        remoteDataSource.getFollower(username).collect {
            if (it.data != null) {
                emit(NetworkResults.Success(it.data))
            }else {
                emit(NetworkResults.Error(it.error))
            }
        }
        emit(NetworkResults.Loading(false))
    }

    override fun getFavorites(): Flow<RoomResults<List<FavoriteData>>> = flow{
        emit(RoomResults.Loading(true))
        localDataSource.getFavorites().collect {
            emit(RoomResults.Loading(false))
            if (it.data != null) {
                emit(RoomResults.Success(it.data))
            }else {
                emit(RoomResults.Error(it.error))
            }
        }

    }

    override fun findFavorites(userId: Int): Flow<RoomResults<List<FavoriteData>>> = flow{
        emit(RoomResults.Loading(true))
        localDataSource.findFavorites(userId).collect {
            emit(RoomResults.Loading(false))
            if (it.data != null) {
                emit(RoomResults.Success(it.data))
            }else {
                emit(RoomResults.Error(it.error))
            }
        }
    }

    override fun insert(values: FavoriteData) {
        launch(Dispatchers.IO){
            localDataSource.insert(values)
        }
    }

    override fun update(values: FavoriteData) {
        launch(Dispatchers.IO){
            localDataSource.update(values)
        }
    }

    override fun delete(values: FavoriteData) {
        launch(Dispatchers.IO){
            localDataSource.delete(values)
        }
    }

}