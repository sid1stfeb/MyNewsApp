package com.android.mynewsapp.other

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import com.android.mynewsapp.R
import com.android.mynewsapp.other.Constant.DATE_FORMAT
import com.android.mynewsapp.other.Constant.SERVER_DATE_FORMAT
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.lang.RuntimeException
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object Util {
    private val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    fun getReadableDate(serverDate:String?) : String{
        return try {
            if(serverDate==null)
                ""
            else {
                val formatter = DateTimeFormatter.ofPattern(SERVER_DATE_FORMAT)
                val formatter2 = DateTimeFormatter.ofPattern(DATE_FORMAT)
                val date = formatter.parse(serverDate)
                formatter2.format(date)
            }
        }catch (ex: DateTimeParseException){
            ""
        }
    }

    fun loadImage(context: Context, url: String?, imageView: ImageView) {
        Glide.with(context).load(url).placeholder(R.drawable.ic_launcher_foreground)
            .centerCrop().apply(requestOptions).into(imageView)
    }

    fun shareNews(context: Context, news: String) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_TEXT, news)
            context.startActivity(Intent.createChooser(shareIntent, null))
        }catch (ex:RuntimeException){
            Log.e("RuntimeException", ex.message?:"RuntimeException")
        }
    }

}