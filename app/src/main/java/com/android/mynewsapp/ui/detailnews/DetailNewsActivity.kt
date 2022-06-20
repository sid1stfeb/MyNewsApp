package com.android.mynewsapp.ui.detailnews

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.android.mynewsapp.data.model.NewsItem
import com.android.mynewsapp.data.network.DataHandler
import com.android.mynewsapp.databinding.ActivityDetailNewsBinding
import com.android.mynewsapp.other.Constant.NEWS_DATA_KEY
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.time.DurationUnit

@AndroidEntryPoint
class DetailNewsActivity : AppCompatActivity() {

    private val detailViewModel: DetailNewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewmodel=detailViewModel
        detailViewModel.newsItem(intent.getParcelableExtra<NewsItem>(NEWS_DATA_KEY) as NewsItem)
    }
}