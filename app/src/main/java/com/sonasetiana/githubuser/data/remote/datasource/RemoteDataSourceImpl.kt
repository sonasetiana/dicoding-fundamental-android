package com.sonasetiana.githubuser.data.remote.datasource

import com.sonasetiana.githubuser.data.model.DetailUserData
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.data.remote.network.BaseResponse
import com.sonasetiana.githubuser.data.remote.network.toApiError
import com.sonasetiana.githubuser.data.remote.services.GitHubService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSourceImpl(
    private val service: GitHubService
) : RemoteDataSource{
    override suspend fun getAllUsers(): Flow<BaseResponse<List<UserData>>> = flow{
        try {
            val response = service.getAllUsers()
            if (response.isSuccessful) {
                emit(BaseResponse(data = response.body()))
            } else  {
                val apiError = response.errorBody().toApiError()
                emit(BaseResponse(error = apiError))
            }
        }catch (t: Throwable) {
            emit(BaseResponse(error = t.toApiError()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun searchUser(keyword: String): Flow<BaseResponse<List<UserData>>> = flow{
        try {
            val response = service.searchUser(keyword)
            if (response.isSuccessful) {
                emit(BaseResponse(data = response.body()?.items))
            } else  {
                val apiError = response.errorBody().toApiError()
                emit(BaseResponse(error = apiError))
            }
        }catch (t: Throwable) {
            emit(BaseResponse(error = t.toApiError()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getDetailUser(username: String): Flow<BaseResponse<DetailUserData>> = flow{
        try {
            val response = service.getDetailUser(username)
            if (response.isSuccessful) {
                emit(BaseResponse(data = response.body()))
            } else  {
                val apiError = response.errorBody().toApiError()
                emit(BaseResponse(error = apiError))
            }
        }catch (t: Throwable) {
            emit(BaseResponse(error = t.toApiError()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getFollowing(username: String): Flow<BaseResponse<List<UserData>>> = flow{
        try {
            val response = service.getFollowing(username)
            if (response.isSuccessful) {
                emit(BaseResponse(data = response.body()))
            } else  {
                val apiError = response.errorBody().toApiError()
                emit(BaseResponse(error = apiError))
            }
        }catch (t: Throwable) {
            emit(BaseResponse(error = t.toApiError()))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getFollower(username: String): Flow<BaseResponse<List<UserData>>> = flow{
        try {
            val response = service.getFollower(username)
            if (response.isSuccessful) {
                emit(BaseResponse(data = response.body()))
            } else {
                val apiError = response.errorBody().toApiError()
                emit(BaseResponse(error = apiError))
            }
        }catch (t: Throwable) {
            emit(BaseResponse(error = t.toApiError()))
        }
    }.flowOn(Dispatchers.IO)
}