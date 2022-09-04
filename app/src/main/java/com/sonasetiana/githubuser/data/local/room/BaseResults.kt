package com.sonasetiana.githubuser.data.local.room

data class BaseResults<out T>(
    val error: String? = null,
    val data: T? = null
)
