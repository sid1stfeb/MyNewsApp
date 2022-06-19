package com.android.mynewsapp.data.network

import com.android.mynewsapp.data.model.NewsList
import retrofit2.Response

interface ApiHelper {
    suspend fun getNews(): Response<NewsList>
}