package com.sonasetiana.githubuser.presentation.detail

import androidx.lifecycle.ViewModel
import com.sonasetiana.githubuser.data.GitHubRepository

class DetailViewModel constructor(
    private val repository: GitHubRepository
): ViewModel() {
}