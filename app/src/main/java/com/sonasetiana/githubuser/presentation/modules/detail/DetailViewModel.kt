package com.sonasetiana.githubuser.presentation.modules.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.sonasetiana.githubuser.base.BaseViewModel
import com.sonasetiana.githubuser.data.local.room.RoomResults
import com.sonasetiana.githubuser.data.model.DetailUserData
import com.sonasetiana.githubuser.data.model.FavoriteData
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.data.remote.network.NetworkResults
import com.sonasetiana.githubuser.domain.detail.DetailUseCase

class DetailViewModel(
    private val useCase: DetailUseCase
) : BaseViewModel() {

    fun getDetailUser(username: String) : LiveData<NetworkResults<DetailUserData>> {
        return useCase.getDetailUser(username).asLiveData()
    }
    fun getFollowing(username: String) : LiveData<NetworkResults<List<UserData>>> {
        return useCase.getFollowing(username).asLiveData()
    }
    fun getFollower(username: String) : LiveData<NetworkResults<List<UserData>>> {
        return useCase.getFollower(username).asLiveData()
    }

    fun checkIsFavorite(userId: Int) : LiveData<RoomResults<List<FavoriteData>>> {
        return useCase.findFavorite(userId).asLiveData()
    }

    fun saveFavorite(values: FavoriteData) {
        useCase.saveFavorite(values)
    }

    fun deleteFavorite(values: FavoriteData) {
        useCase.deleteFavorite(values)
    }
}