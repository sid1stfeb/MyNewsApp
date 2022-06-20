package com.android.mynewsapp.data.network

import com.android.mynewsapp.data.model.NewsList
import com.android.mynewsapp.other.Constant.TOP_HEADLINE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(TOP_HEADLINE)
    suspend fun getNews(@Query("country") country:String,
                        @Query("page") page:Int): Response<NewsList>
}