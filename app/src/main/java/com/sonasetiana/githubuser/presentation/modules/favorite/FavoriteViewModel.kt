package com.sonasetiana.githubuser.presentation.modules.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.sonasetiana.githubuser.base.BaseViewModel
import com.sonasetiana.githubuser.data.local.room.RoomResults
import com.sonasetiana.githubuser.data.model.FavoriteData
import com.sonasetiana.githubuser.domain.favorite.FavoriteUseCase

class FavoriteViewModel(
    private val useCase: FavoriteUseCase
) : BaseViewModel() {

    fun getFavorites() : LiveData<RoomResults<List<FavoriteData>>> {
        return useCase.getFavorites().asLiveData()
    }

    fun deleteFavorite(values: FavoriteData) = useCase.deleteFavorite(values)
}