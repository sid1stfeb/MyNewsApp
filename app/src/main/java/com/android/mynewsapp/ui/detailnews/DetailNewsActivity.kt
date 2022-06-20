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
        if(isFav)
            menu.getItem(1).setIcon(R.drawable.ic_action_fav_filled)
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
                val toastStr:Int
                val iconId:Int
                if(isFav){
                    toastStr=R.string.fav_removed
                    iconId=R.drawable.ic_action_fav
                    isFav=false
                }else{
                    toastStr=R.string.fav_added
                    iconId=R.drawable.ic_action_fav_filled
                    isFav=true
                }
                Toast.makeText(this,
                    toastStr, Toast.LENGTH_SHORT).show()
                item.setIcon(iconId)
                true
            }
            R.id.share->{
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "${newsItem.title}\n\nRead entire article here:\n${newsItem.url}")
                startActivity(Intent.createChooser(shareIntent, "choose one"))
                true
            }
            else-> super.onOptionsItemSelected(item)
        }

    }
}