package com.sonasetiana.githubuser.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {

    companion object {
        private val BASE_URL = "https://api.github.com/"
        fun newInstance() : NetworkModule {
            return NetworkModule()
        }
    }

    private fun getInterceptor() : Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.HEADERS)
    }

    private fun getClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getInterceptor())
            .addInterceptor(BaseInterceptor())
            .build()
    }

    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .client(getClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}