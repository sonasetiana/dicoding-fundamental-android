package com.sonasetiana.githubuser.data.local.room

enum class RoomStatus {
    SUCCESS,
    ERROR,
    LOADING
}

sealed class RoomResults <out T> (
    val status: RoomStatus,
    val data: T?,
    val error: String?
){
    data class Loading<out R> (val isLoading: Boolean) : RoomResults<R>(
        status = RoomStatus.LOADING,
        data = null,
        error = null
    )

    data class Success<out R> (val values: R?) : RoomResults<R>(
        status = RoomStatus.SUCCESS,
        data = values,
        error = null
    )

    data class Error<out R> (val message: String?) : RoomResults<R>(
        status = RoomStatus.ERROR,
        data = null,
        error = message
    )
}
