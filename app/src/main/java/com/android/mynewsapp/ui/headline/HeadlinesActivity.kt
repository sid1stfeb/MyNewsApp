package com.android.mynewsapp.ui.headline

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mynewsapp.R
import com.android.mynewsapp.data.model.NewsList
import com.android.mynewsapp.data.network.DataHandler
import com.android.mynewsapp.databinding.ActivityHeadlineBinding
import com.android.mynewsapp.other.Constant.NEWS_DATA_KEY
import com.android.mynewsapp.ui.detailnews.DetailNewsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeadlinesActivity : AppCompatActivity() {

    private val viewModel: HeadlineViewModel by viewModels()
    private lateinit var adapter: HeadlineAdapter
    private lateinit var binding: ActivityHeadlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = HeadlineAdapter(HeadlineAdapter.OnClickListener{
            val intent = Intent(this, DetailNewsActivity::class.java).apply {
                putExtra(NEWS_DATA_KEY, it)
            }
            startActivity(intent)
        })
        viewModel.getNews()
        binding.rvHeadlineList.layoutManager = LinearLayoutManager(this)
        binding.rvHeadlineList.adapter=adapter
        binding.swipeContainer.setOnRefreshListener {
            viewModel.getNews()
            Log.d("HeadlineActivity:", "swipeContainer refresh")
        }

        viewModel.headlines.observe(this) {
            Log.d("HeadlineActivity:", "headlines.observe: ${it.status}")
            when (it.status) {
                DataHandler.Status.SUCCESS -> {
                    val newsList: NewsList? = it.data
                    Log.d("HeadlineActivity NetworkResponse:", "Data: $newsList")
                    if (newsList != null)
                        adapter.setData(newsList.articles)

                    if (binding.swipeContainer.isRefreshing) {
                        binding.swipeContainer.isRefreshing = false
                    }
                }
                DataHandler.Status.ERROR -> {
                    if (binding.swipeContainer.isRefreshing) {
                        binding.swipeContainer.isRefreshing = false
                    }
                    Toast.makeText(this, R.string.something_wrng, LENGTH_SHORT).show()
                }
                DataHandler.Status.LOADING -> {
                    if (!binding.swipeContainer.isRefreshing) {
                        binding.swipeContainer.isRefreshing = true
                    }
                }
            }
        }
    }
}