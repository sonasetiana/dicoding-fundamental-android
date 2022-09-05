package com.sonasetiana.githubuser.data.remote.network

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.MalformedJsonException
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

object NetworkConstant {
    const val HTTP_BAD_REQUEST = 400
    const val HTTP_UNAUTHORIZED = 401
    const val SERVER_ERROR = 500

    const val LOCAL_TIMEOUT = 0
    const val GENERAL_ERROR = 99

    const val MSG_RTO = "Connection timeout. Make sure you have an internet connection."
    const val MSG_PARSE_ERROR = "Failed json parse."
    const val MSG_UNKNOWN = "Oops, something wrong. Please try again later."
}

data class ApiError(
    @SerializedName("code")
    var code: Int,
    @SerializedName("message")
    var message: String?
)

fun ResponseBody?.toApiError() : ApiError{
    val gson = Gson()
    return gson.fromJson(this?.charStream(), ApiError::class.java)
}

fun Throwable.toApiError(): ApiError {
    return when (this) {
        is HttpException -> {
            this.getErrorDetail()
        }
        is IOException -> {
            this.getErrorDetail()
        }
        is JsonParseException -> {
            ApiError(
                code = NetworkConstant.GENERAL_ERROR,
                message = NetworkConstant.MSG_PARSE_ERROR
            )
        }
        else -> {
            ApiError(
                code = NetworkConstant.GENERAL_ERROR,
                message = NetworkConstant.MSG_UNKNOWN
            )
        }
    }
}

fun HttpException.getErrorDetail(): ApiError {
    return when (this.code()) {
        NetworkConstant.LOCAL_TIMEOUT -> {
            ApiError(
                code = NetworkConstant.GENERAL_ERROR,
                message = NetworkConstant.MSG_RTO
            )
        }
        HttpURLConnection.HTTP_INTERNAL_ERROR -> {
            ApiError(
                code = NetworkConstant.SERVER_ERROR,
                message = NetworkConstant.MSG_UNKNOWN
            )
        }
        in HttpURLConnection.HTTP_BAD_REQUEST..HttpURLConnection.HTTP_UNSUPPORTED_TYPE -> {
            val response = this.response()?.errorBody()?.toString()
            return if (response.isNullOrEmpty()) {
                ApiError(
                    code = NetworkConstant.SERVER_ERROR,
                    message = NetworkConstant.MSG_UNKNOWN
                )
            } else {
                this.response()?.errorBody()?.toApiError()!!
            }
        }
        else -> {
            ApiError(
                code = NetworkConstant.GENERAL_ERROR,
                message = NetworkConstant.MSG_UNKNOWN
            )
        }
    }
}

fun IOException.getErrorDetail(): ApiError {
    return when (this) {
        is SocketTimeoutException -> {
            ApiError(
                code = NetworkConstant.LOCAL_TIMEOUT,
                message = NetworkConstant.MSG_RTO
            )
        }
        is MalformedJsonException -> {
            ApiError(
                code = NetworkConstant.GENERAL_ERROR,
                message = NetworkConstant.MSG_PARSE_ERROR
            )
        }
        else -> {
            ApiError(
                code = NetworkConstant.GENERAL_ERROR,
                message = NetworkConstant.MSG_UNKNOWN
            )
        }
    }
}