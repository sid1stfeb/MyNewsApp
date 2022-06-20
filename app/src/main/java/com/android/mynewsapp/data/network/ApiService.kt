package com.android.mynewsapp.data.network

import com.android.mynewsapp.data.model.NewsList
import com.android.mynewsapp.other.Constant.TOP_HEADLINE
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(TOP_HEADLINE)
    suspend fun getNews(): Response<NewsList>
}