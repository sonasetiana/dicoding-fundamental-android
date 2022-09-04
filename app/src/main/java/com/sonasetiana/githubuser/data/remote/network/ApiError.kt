package com.sonasetiana.githubuser.data.remote.network

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.MalformedJsonException
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

data class ApiError(
    var code: String,
    var message: String
)

data class ErrorResponse(
    @SerializedName("message")
    val message: String?,

    @SerializedName("code")
    val code: String?
)

object NetworkConstant {

    const val JSON_STATUS = "status"

    const val STATUS_SUCCESS = "ok"
    const val STATUS_ERROR = "error"

    const val HTTP_BAD_REQUEST = 400
    const val HTTP_UNAUTHORIZED = 401

    const val LOCAL_TIMEOUT = 0

    const val ERROR_GENERAL = "ERROR_GENERAL"
    const val ERROR_TIMEOUT = "ERROR_TIMEOUT"
    const val ERROR_CONNECTION = "ERROR_NO_INTERNET_CONNECTION"
    const val ERROR_JSON_PARSING = "ERROR_JSON_PARSING"
    const val ERROR_JSON_MALFORMED = "ERROR_JSON_MALFORMED"
    const val ERROR_SERVER = "ERROR_SERVER"

}


fun Throwable.getApiError(): ApiError {
    return when (this) {
        is HttpException -> {
            this.getHttpExceptionError()
        }
        is IOException -> {
            this.getIOExceptionError()
        }
        is JsonParseException -> {
            ApiError(
                code = NetworkConstant.ERROR_JSON_PARSING,
                message = NetworkConstant.ERROR_JSON_PARSING
            )
        }
        else -> {
            ApiError(
                code = NetworkConstant.ERROR_GENERAL,
                message = NetworkConstant.ERROR_GENERAL
            )
        }
    }
}

fun HttpException.getHttpExceptionError(): ApiError {
    return when (this.code()) {
        NetworkConstant.LOCAL_TIMEOUT -> {
            ApiError(
                code = NetworkConstant.ERROR_TIMEOUT,
                message = NetworkConstant.ERROR_TIMEOUT
            )
        }
        HttpURLConnection.HTTP_INTERNAL_ERROR -> {
            ApiError(
                code = NetworkConstant.ERROR_SERVER,
                message = NetworkConstant.ERROR_SERVER
            )
        }
        in HttpURLConnection.HTTP_BAD_REQUEST..HttpURLConnection.HTTP_UNSUPPORTED_TYPE -> {
            val response = this.response()?.errorBody()?.toString()
            return if (response.isNullOrEmpty()) {
                ApiError(
                    code = NetworkConstant.ERROR_GENERAL,
                    message = NetworkConstant.ERROR_GENERAL
                )
            } else {
                val gson = Gson()
                val errorResponse = gson.fromJson(response, ErrorResponse::class.java)
                ApiError(
                    code = errorResponse.code ?: NetworkConstant.ERROR_GENERAL,
                    message = errorResponse.message ?: NetworkConstant.ERROR_GENERAL
                )
            }
        }
        else -> {
            ApiError(
                code = NetworkConstant.ERROR_GENERAL,
                message = NetworkConstant.ERROR_GENERAL
            )
        }
    }
}

fun IOException.getIOExceptionError(): ApiError {
    return when (this) {
        is SocketTimeoutException -> {
            ApiError(
                code = NetworkConstant.ERROR_TIMEOUT,
                message = NetworkConstant.ERROR_TIMEOUT
            )
        }
        is MalformedJsonException -> {
            ApiError(
                code = NetworkConstant.ERROR_JSON_MALFORMED,
                message = NetworkConstant.ERROR_JSON_MALFORMED
            )
        }
        else -> {
            ApiError(
                code = NetworkConstant.ERROR_CONNECTION,
                message = NetworkConstant.ERROR_CONNECTION
            )
        }
    }
}