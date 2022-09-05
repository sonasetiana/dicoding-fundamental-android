package com.sonasetiana.githubuser.data.remote.network

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(BaseInterceptor())
            .addInterceptor(getInterceptor())
            .build()
    }

    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .client(getClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }
}