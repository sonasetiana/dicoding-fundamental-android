package com.sonasetiana.githubuser.data

import com.sonasetiana.githubuser.data.local.datasource.FavoriteDataSource
import com.sonasetiana.githubuser.data.remote.GitHubService

class GitHubRepository constructor(
    private val service: GitHubService,
    private val localDataSource: FavoriteDataSource
) {
    suspend fun getAllUsers() = service.getAllUsers()

    suspend fun searchUser(keyword: String) = service.searchUser(keyword = keyword)

    suspend fun getDetailUser(username: String) = service.getDetailUser(username = username)

    suspend fun getFollower(username: String) = service.getFollower(username = username)

    suspend fun getFollowing(username: String) = service.getFollowing(username = username)
}