package com.sonasetiana.githubuser.data.remote.network

import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
        newRequest.addHeader("Authorization", "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")
        return chain.proceed(newRequest.build())
    }
}