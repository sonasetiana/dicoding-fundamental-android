package com.sonasetiana.githubuser.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), BaseViewModelContact, CoroutineScope{
    var job: Job? = null
    private val loading = MutableLiveData<Boolean>()

    override fun getIsLoading() = loading

    override fun setIsLoading(isLoading: Boolean) {
        this.loading.value = isLoading
    }

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}