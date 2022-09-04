package com.sonasetiana.githubuser.domain.detail

import com.sonasetiana.githubuser.data.local.room.RoomResults
import com.sonasetiana.githubuser.data.model.DetailUserData
import com.sonasetiana.githubuser.data.model.FavoriteData
import com.sonasetiana.githubuser.data.model.UserData
import com.sonasetiana.githubuser.data.remote.network.NetworkResults
import com.sonasetiana.githubuser.data.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow

class DetailInteractor(
    private val repository: GitHubRepository
) : DetailUseCase {
    override fun getDetailUser(username: String): Flow<NetworkResults<DetailUserData>> {
        return repository.getDetailUser(username)
    }

    override fun getFollowing(username: String): Flow<NetworkResults<List<UserData>>> {
        return repository.getFollowing(username)
    }

    override fun getFollower(username: String): Flow<NetworkResults<List<UserData>>> {
        return repository.getFollower(username)
    }

    override fun findFavorite(userId: Int): Flow<RoomResults<List<FavoriteData>>> {
        return repository.findFavorites(userId)
    }

    override fun saveFavorite(values: FavoriteData) {
        repository.insert(values)
    }

    override fun deleteFavorite(values: FavoriteData) {
        repository.delete(values)
    }

}