package com.sonasetiana.githubuser.data.remote.network

import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val headers = request().headers
        val builder = request().newBuilder()
        builder.addHeader("Authorization", " token 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")

        /**
         * Keep this line of codes at the bottom of function
         * Because, OkHttp Headers is NOT a map.
         * */
        headers.names().forEach { key ->
            headers[key]?.let { value ->
                builder.removeHeader(key)
                builder.addHeader(key, value)
            }
        }
        proceed(builder.build())
    }
}