package com.android.mynewsapp.data.network

import com.android.mynewsapp.data.model.NewsList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("employees")
    suspend fun getNews(): Response<NewsList>
}