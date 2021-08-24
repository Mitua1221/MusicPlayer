package com.arjental.musicplayer.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    private val okHttpClient: OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .build()

    private val gson: Gson = GsonBuilder().setLenient().create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: AlbumsApi by lazy {
        retrofit.create(AlbumsApi::class.java)
    }

    private companion object {
        private const val BASE_URL = "https://api.mobimusic.kz/"
    }

}