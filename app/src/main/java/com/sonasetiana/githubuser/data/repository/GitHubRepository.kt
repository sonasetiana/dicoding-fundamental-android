package com.sonasetiana.githubuser.data.repository

import com.sonasetiana.githubuser.data.local.room.RoomResults
import com.sonasetiana.githubuser.data.model.DetailUserData
import com.sonasetiana.githubuser.data.model.FavoriteData
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.data.remote.network.NetworkResults
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {
    fun getAllUsers() : Flow<NetworkResults<List<UserData>>>
    fun searchUser(keyword: String) : Flow<NetworkResults<List<UserData>>>
    fun getDetailUser(username: String) : Flow<NetworkResults<DetailUserData>>
    fun getFollowing(username: String) : Flow<NetworkResults<List<UserData>>>
    fun getFollower(username: String) : Flow<NetworkResults<List<UserData>>>

    fun getFavorites(): Flow<RoomResults<List<FavoriteData>>>
    fun findFavorites(userId: Int): Flow<RoomResults<List<FavoriteData>>>
    fun insert(values: FavoriteData)
    fun update(values: FavoriteData)
    fun delete(values: FavoriteData)
}