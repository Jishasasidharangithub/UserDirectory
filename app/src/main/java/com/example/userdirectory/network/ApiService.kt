package com.example.userdirectory.network

import com.example.userdirectory.model.response.UserListResponseItem
import com.example.userdirectory.model.response.UserPostResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun usersList(): Response<List<UserListResponseItem>>

    @GET("posts")
    suspend fun usersPost(@Query("userId") userId: Int): Response<List<UserPostResponseItem>>
}
