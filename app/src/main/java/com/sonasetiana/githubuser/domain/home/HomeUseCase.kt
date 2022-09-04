package com.sonasetiana.githubuser.domain.home

import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.data.remote.network.NetworkResults
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    fun getAllUsers() : Flow<NetworkResults<List<UserData>>>
    fun searchUser(keyword: String) : Flow<NetworkResults<List<UserData>>>
}