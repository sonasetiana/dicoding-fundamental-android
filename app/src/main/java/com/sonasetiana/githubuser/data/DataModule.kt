package com.sonasetiana.githubuser.data

import android.app.Application
import com.sonasetiana.githubuser.data.local.room.GithubRoomDatabase
import com.sonasetiana.githubuser.data.local.room.dao.FavoriteDao
import com.sonasetiana.githubuser.data.remote.GitHubService

object DataModule {
    fun provideRepository() : GitHubRepository {
        val service = GitHubService.create()
        return GitHubRepository(service = service)
    }

    fun provideGithubRoomDatabase(application: Application) : GithubRoomDatabase {
        return GithubRoomDatabase.getDatabase(application)
    }

    fun provideFavoriteDao(application: Application) : FavoriteDao {
        return provideGithubRoomDatabase(application).favoriteDao()
    }
}