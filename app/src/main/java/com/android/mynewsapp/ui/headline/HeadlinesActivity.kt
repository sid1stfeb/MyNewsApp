package com.android.mynewsapp.ui.headline

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        fetchData()
    }

    private fun initializeView(){
        adapter = HeadlineAdapter(HeadlineAdapter.OnClickListener{
            val intent = Intent(this, DetailNewsActivity::class.java).apply {
                putExtra(NEWS_DATA_KEY, it)
            }
            startActivity(intent)
        })
        binding.rvHeadlineList.layoutManager = LinearLayoutManager(this)
        binding.rvHeadlineList.adapter=adapter

        binding.swipeContainer.setOnRefreshListener {
            page=1
            adapter.clearData()
            fetchData()
            Log.d("HeadlineActivity:", "swipeContainer refresh")
        }
        viewModel.headlines.observe(this) {
            Log.d("HeadlineActivity:", "headlines.observe: ${it.status}")
            when (it.status) {
                DataHandler.Status.SUCCESS -> {
                    setDataInList(it.data)
                }
                DataHandler.Status.ERROR -> {
                    pullRefreshLoader(false)
                    Toast.makeText(this, R.string.something_wrng, LENGTH_SHORT).show()
                }
                DataHandler.Status.LOADING -> {
                    pullRefreshLoader(true)
                }
            }
        }

        binding.rvHeadlineList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                val totalItemCount = layoutManager!!.itemCount
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                val endHasBeenReached = lastVisible + 5 >= totalItemCount
                if (totalItemCount > 0 && endHasBeenReached && page<=5) {
                    viewModel.getNews(page)
                    page++
                }
            }
        })
    }

    private fun setDataInList(newsList: NewsList?){
        Log.d("HeadlineActivity NetworkResponse:", "Data: $newsList")
        if ((newsList?.articles?.size ?: 0) > 0)
            adapter.setData(newsList!!.articles)
        else
            page=6

        pullRefreshLoader(false)
    }

    private fun fetchData() {
        viewModel.getNews(page)
        page++
    }

    private fun pullRefreshLoader(refresh:Boolean){
            binding.swipeContainer.isRefreshing = refresh
    }
}