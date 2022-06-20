package com.android.mynewsapp.data.network

import com.android.mynewsapp.data.model.NewsList
import com.android.mynewsapp.other.Constant.COUNTRY_ID
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService):ApiHelper{

    override suspend fun getNews(page:Int): Response<NewsList> = apiService.getNews(COUNTRY_ID, page)

}