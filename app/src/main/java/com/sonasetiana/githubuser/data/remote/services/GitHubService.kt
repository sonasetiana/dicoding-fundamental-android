package com.sonasetiana.githubuser.data.remote.services

import com.sonasetiana.githubuser.data.model.DetailUserData
import com.sonasetiana.githubuser.data.model.SearchUserGitHubResponse
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.data.remote.network.NetworkModule
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("users")
    suspend fun getAllUsers() : Response<List<UserData>>

    @GET("search/users")
    suspend fun searchUser(@Query("q") keyword: String) : Response<SearchUserGitHubResponse>

    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username: String) : Response<DetailUserData>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username", encoded = false) username: String) : Response<List<UserData>>

    @GET("users/{username}/followers")
    suspend fun getFollower(@Path("username", encoded = false) username: String) : Response<List<UserData>>

    companion object {
        fun create() : GitHubService {
            return NetworkModule.newInstance().getRetrofit().create(GitHubService::class.java)
        }
    }

}