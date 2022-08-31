package com.sonasetiana.githubuser.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import retrofit2.HttpException
import java.io.IOException
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

    fun exceptionHandler(error: SingleLiveEvent<String>) : CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, throwable ->
            when (throwable) {
                is IOException -> {
                    error.postValue("Network Error")
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = throwable.message()
                    error.postValue("Error $code $errorResponse")
                }
                else -> {
                    error.postValue("Unknown Error")
                }
            }
        }
    }
}