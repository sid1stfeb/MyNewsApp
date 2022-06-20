package com.android.mynewsapp.data.network

import com.android.mynewsapp.data.model.NewsList
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService):ApiHelper{

    override suspend fun getNews(): Response<NewsList> = apiService.getNews()

}