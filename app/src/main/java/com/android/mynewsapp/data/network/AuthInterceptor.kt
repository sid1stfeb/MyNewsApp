package com.android.mynewsapp.data.network

import com.android.mynewsapp.other.Constant.NEWS_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", NEWS_API_KEY)
        return chain.proceed(requestBuilder.build())
    }
}