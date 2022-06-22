package com.android.mynewsapp.ui.detailnews

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.mynewsapp.R
import com.android.mynewsapp.data.model.NewsItem
import com.android.mynewsapp.other.Util
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailNewsViewModel @Inject constructor() : ViewModel() {
    private val _headline = MutableLiveData<String?>()
    private val _detail = MutableLiveData<String?>()
    private val _time =  MutableLiveData<String?>()
    private val _author =  MutableLiveData<String?>()
    private val _image =  MutableLiveData<String?>()

    val headline : LiveData<String?> = _headline
    val detail : LiveData<String?> = _detail
    val time : LiveData<String?> = _time
    val author : LiveData<String?> = _author
    val image : LiveData<String?> = _image

    fun newsItem(newsItem: NewsItem) {
        Log.d("DetailNewsViewModel:", "Data: $newsItem" )
        _headline.value = newsItem.title
        _detail.value = newsItem.description
        _time.value = Util.getReadableDate(newsItem.publishedAt)
        _author.value = newsItem.author
        _image.value = newsItem.urlToImage
    }

}
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(view.context).load(url).placeholder(R.drawable.ic_launcher_foreground).centerCrop().apply(requestOptions).into(view)
}