package com.example.huertohogar.data.repository

import com.example.huertohogar.data.remote.RetrofitInstance
import com.example.huertohogar.model.Post

class PostRepository {

    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }
}