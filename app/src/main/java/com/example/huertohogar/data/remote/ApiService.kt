package com.example.huertohogar.data.remote

import com.example.huertohogar.model.Post
import retrofit2.http.GET

interface ApiService {
    @GET
    suspend fun getPosts(): List<Post>
}