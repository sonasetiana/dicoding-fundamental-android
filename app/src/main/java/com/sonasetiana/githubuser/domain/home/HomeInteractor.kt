package com.sonasetiana.githubuser.domain.home

import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.data.remote.network.NetworkResults
import com.sonasetiana.githubuser.data.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow

class HomeInteractor(
    private val repository: GitHubRepository
) : HomeUseCase {
    override fun getAllUsers(): Flow<NetworkResults<List<UserData>>> {
        return repository.getAllUsers()
    }

    override fun searchUser(keyword: String): Flow<NetworkResults<List<UserData>>> {
        return repository.searchUser(keyword)
    }

}