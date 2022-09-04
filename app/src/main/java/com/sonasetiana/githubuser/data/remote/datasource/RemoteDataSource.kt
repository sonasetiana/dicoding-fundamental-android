package com.sonasetiana.githubuser.data.remote.datasource

import com.sonasetiana.githubuser.data.model.DetailUserData
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.data.remote.network.BaseResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun getAllUsers() : Flow<BaseResponse<List<UserData>>>
    suspend fun searchUser(keyword: String) : Flow<BaseResponse<List<UserData>>>
    suspend fun getDetailUser(username: String) : Flow<BaseResponse<DetailUserData>>
    suspend fun getFollowing(username: String) : Flow<BaseResponse<List<UserData>>>
    suspend fun getFollower(username: String) : Flow<BaseResponse<List<UserData>>>

}