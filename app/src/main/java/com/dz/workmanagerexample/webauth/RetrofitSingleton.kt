package com.dz.workmanagerexample.webauth

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitSingleton {

    fun getRetrofitInstance() : ApiResponse {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val interceptor = httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        return Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiResponse::class.java)
    }
}