package com.sonasetiana.githubuser.base

import androidx.lifecycle.LiveData

interface BaseViewModelContact {
    fun getIsLoading(): LiveData<Boolean>

    fun setIsLoading(isLoading: Boolean)
}