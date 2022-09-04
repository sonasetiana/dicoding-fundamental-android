package com.sonasetiana.githubuser.data.remote.network

enum class NetworkStatus {
    SUCCESS,
    ERROR,
    LOADING
}

sealed class NetworkResults <out T> (
    val status: NetworkStatus,
    val data: T?,
    val error: ApiError?
){
    data class Loading<out R> (val isLoading: Boolean) : NetworkResults<R>(
        status = NetworkStatus.LOADING,
        data = null,
        error = null
    )

    data class Success<out R> (val values: R?) : NetworkResults<R>(
        status = NetworkStatus.SUCCESS,
        data = values,
        error = null
    )

    data class Error<out R> (val apiError: ApiError?) : NetworkResults<R>(
        status = NetworkStatus.ERROR,
        data = null,
        error = apiError
    )
}
