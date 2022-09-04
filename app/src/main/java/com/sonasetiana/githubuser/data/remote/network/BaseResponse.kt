package com.sonasetiana.githubuser.data.remote.network

data class BaseResponse<out T>(
    val error: ApiError? = null,
    val data: T? = null
)
