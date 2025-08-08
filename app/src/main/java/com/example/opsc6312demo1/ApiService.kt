package com.example.opsc6312demo1

import retrofit2.http.GET

interface ApiService {
    @GET("posts/1")
    suspend fun getPost(): Post
}