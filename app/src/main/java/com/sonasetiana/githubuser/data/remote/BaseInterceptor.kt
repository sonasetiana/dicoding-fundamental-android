package com.sonasetiana.githubuser.data.remote

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