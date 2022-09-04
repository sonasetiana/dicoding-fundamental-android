package com.sonasetiana.githubuser.domain.detail

import com.sonasetiana.githubuser.data.local.room.RoomResults
import com.sonasetiana.githubuser.data.model.DetailUserData
import com.sonasetiana.githubuser.data.model.FavoriteData
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.data.remote.network.NetworkResults
import kotlinx.coroutines.flow.Flow

interface DetailUseCase {
    fun getDetailUser(username: String) : Flow<NetworkResults<DetailUserData>>
    fun getFollowing(username: String) : Flow<NetworkResults<List<UserData>>>
    fun getFollower(username: String) : Flow<NetworkResults<List<UserData>>>
    fun findFavorite(userId: Int) : Flow<RoomResults<List<FavoriteData>>>
    fun saveFavorite(values: FavoriteData)
    fun deleteFavorite(values: FavoriteData)
}