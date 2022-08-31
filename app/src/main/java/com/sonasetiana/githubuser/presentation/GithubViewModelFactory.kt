package com.sonasetiana.githubuser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sonasetiana.githubuser.data.GitHubRepository
import com.sonasetiana.githubuser.presentation.detail.DetailViewModel
import com.sonasetiana.githubuser.presentation.main.MainViewModel

class GithubViewModelFactory constructor(
    private val repository: GitHubRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(repository = repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            DetailViewModel(repository = repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}