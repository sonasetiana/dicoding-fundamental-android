package com.sonasetiana.githubuser.domain.favorite

import com.sonasetiana.githubuser.data.local.room.RoomResults
import com.sonasetiana.githubuser.data.model.FavoriteData
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {
    fun getFavorites() : Flow<RoomResults<List<FavoriteData>>>
    fun deleteFavorite(values: FavoriteData)
}