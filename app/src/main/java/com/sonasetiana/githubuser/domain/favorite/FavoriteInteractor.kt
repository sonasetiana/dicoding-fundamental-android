package com.sonasetiana.githubuser.domain.favorite

import com.sonasetiana.githubuser.data.local.room.RoomResults
import com.sonasetiana.githubuser.data.model.FavoriteData
import com.sonasetiana.githubuser.data.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow

class FavoriteInteractor(
    private val repository: GitHubRepository
) : FavoriteUseCase {
    override fun getFavorites(): Flow<RoomResults<List<FavoriteData>>> {
        return repository.getFavorites()
    }

    override fun deleteFavorite(values: FavoriteData) {
        repository.delete(values)
    }
}