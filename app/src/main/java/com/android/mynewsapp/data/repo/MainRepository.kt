package com.android.mynewsapp.data.repo

import com.android.mynewsapp.data.network.ApiHelper
import javax.inject.Inject

class MainRepository  @Inject constructor(private val apiHelper: ApiHelper){
    suspend fun getNews() = apiHelper.getNews()
}