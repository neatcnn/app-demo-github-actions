package com.example.opsc6312demo1

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit = createRetrofit("https://jsonplaceholder.typicode.com/")

    val api: ApiService
        get() = retrofit.create(ApiService::class.java)

    fun changeBaseUrl(baseUrl: String) {
        retrofit = createRetrofit(baseUrl)
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
