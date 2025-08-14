package com.example.opsc6312demo1

class PostHandler(private val api: ApiService) {

    suspend fun fetchPost(): Result<Post> {
        return try {
            val post = api.getPost()
            Result.success(post)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}