package com.sonasetiana.githubuser.data

import com.sonasetiana.githubuser.data.remote.GitHubService

object DataModule {
    fun provideRepository() : GitHubRepository {
        val service = GitHubService.create()
        return GitHubRepository(service = service)
    }
}