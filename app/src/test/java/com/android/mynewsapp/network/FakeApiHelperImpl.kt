package com.android.mynewsapp.network

import com.android.mynewsapp.data.model.NewsItem
import com.android.mynewsapp.data.model.NewsList
import com.android.mynewsapp.data.network.ApiHelper
import retrofit2.Response

class FakeApiHelperImpl : ApiHelper {
    override suspend fun getNews(page: Int): Response<NewsList>{
        return when (page) {
            2 -> {
                success2()
            }
            else -> {
                success()
            }
        }


    }
    companion object Construct{
        fun success(): Response<NewsList>{
            val arr=ArrayList<NewsItem>()
            arr.addAll(FakeData.newsList)
            return Response.success(NewsList(arr))
        }
        fun success2(): Response<NewsList>{
            val arr=ArrayList<NewsItem>()
            arr.addAll(FakeData.newsList2)
            return Response.success(NewsList(arr))
        }
    }
}
