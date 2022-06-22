package com.android.mynewsapp.ui.detailnews

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.mynewsapp.R
import com.android.mynewsapp.data.model.NewsItem
import com.android.mynewsapp.databinding.ActivityDetailNewsBinding
import com.android.mynewsapp.other.Constant.NEWS_DATA_KEY
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailNewsActivity : AppCompatActivity() {

    private val detailViewModel: DetailNewsViewModel by viewModels()
    private lateinit var newsItem: NewsItem
    private var isFav: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title=getString(R.string.detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.viewmodel=detailViewModel
        newsItem = intent.getParcelableExtra<NewsItem>(NEWS_DATA_KEY) as NewsItem
        detailViewModel.newsItem(newsItem)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->{
                onBackPressed()
                true
            }
            R.id.fav->{
                Toast.makeText(this,
                    R.string.fav_added, Toast.LENGTH_SHORT).show()
                setFavIcon(item)
                true
            }
            R.id.share->{
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                    String.format(getString(R.string.share_text), newsItem.title, newsItem.url))
                startActivity(Intent.createChooser(shareIntent, null))
                true
            }
            else-> super.onOptionsItemSelected(item)
        }

    }

    private fun setFavIcon(item: MenuItem){
        val toastStr:Int
        val iconId:Int
        val contentDesc:String
        if(isFav){
            toastStr=R.string.fav_removed
            contentDesc=getString(R.string.fav_add)
            iconId=R.drawable.ic_action_fav
            isFav=false
        }else{
            toastStr=R.string.fav_added
            contentDesc=getString(R.string.fav_remove)
            iconId=R.drawable.ic_action_fav_filled
            isFav=true
        }
        Toast.makeText(this,
            toastStr, Toast.LENGTH_SHORT).show()
        item.setIcon(iconId)
        item.title = contentDesc
    }
}