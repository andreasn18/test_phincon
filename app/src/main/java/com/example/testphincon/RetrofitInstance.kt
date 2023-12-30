package com.example.testphincon

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    private const val BASE_URL2 = "https://api-q2wixue6fq-et.a.run.app/"
    val api: ApiService by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofit.create(ApiService::class.java)
    }
    val api2: ApiService by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL2).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofit.create(ApiService::class.java)
    }
}