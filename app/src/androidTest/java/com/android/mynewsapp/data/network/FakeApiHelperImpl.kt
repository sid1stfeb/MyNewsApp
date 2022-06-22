package com.android.mynewsapp.data.network

import com.android.mynewsapp.data.model.NewsItem
import com.android.mynewsapp.data.model.NewsList
import retrofit2.Response

class FakeApiHelperImpl :ApiHelper {
    override suspend fun getNews(page: Int): Response<NewsList>{
        val arr=ArrayList<NewsItem>()
        arr.addAll(FakeData.newsList)
        return Response.success(NewsList(arr))

    }
}
