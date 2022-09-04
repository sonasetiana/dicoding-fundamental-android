package com.sonasetiana.githubuser.data

import android.app.Application
import com.sonasetiana.githubuser.data.local.datasource.LocalDataSource
import com.sonasetiana.githubuser.data.local.datasource.LocalDataSourceImpl
import com.sonasetiana.githubuser.data.local.room.GithubRoomDatabase
import com.sonasetiana.githubuser.data.local.room.dao.FavoriteDao
import com.sonasetiana.githubuser.data.remote.datasource.RemoteDataSource
import com.sonasetiana.githubuser.data.remote.datasource.RemoteDataSourceImpl
import com.sonasetiana.githubuser.data.remote.services.GitHubService
import com.sonasetiana.githubuser.data.repository.GitHubRepository
import com.sonasetiana.githubuser.data.repository.GitHubRepositoryImpl

object DataModule {

    fun provideGithubRoomDatabase(application: Application) : GithubRoomDatabase {
        return GithubRoomDatabase.getDatabase(application)
    }

    fun provideFavoriteDao(application: Application) : FavoriteDao {
        return provideGithubRoomDatabase(application).favoriteDao()
    }

    fun provideGithubApiService(): GitHubService {
        return GitHubService.create()
    }

    fun provideLocalDataSource(application: Application) : LocalDataSource {
        return LocalDataSourceImpl(provideFavoriteDao(application))
    }

    fun provideRemoteDataSource() : RemoteDataSource {
        return RemoteDataSourceImpl(provideGithubApiService())
    }

    fun provideRepository(application: Application) : GitHubRepository {
        val local = provideLocalDataSource(application)
        val remote = provideRemoteDataSource()
        return GitHubRepositoryImpl(localDataSource = local, remoteDataSource = remote)
    }
}