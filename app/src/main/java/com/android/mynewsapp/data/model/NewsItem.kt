package com.android.mynewsapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsItem(val author:String?="",val title:String?="",val description:String?="",
                    val url:String?="",val urlToImage:String?="",val publishedAt:String?=""): Parcelable
