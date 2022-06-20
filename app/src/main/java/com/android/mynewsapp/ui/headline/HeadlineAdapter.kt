package com.android.mynewsapp.ui.headline

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mynewsapp.R
import com.android.mynewsapp.data.model.NewsItem
import com.android.mynewsapp.databinding.ItemHeadlineBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.time.format.DateTimeFormatter

class HeadlineAdapter (private val onClickListener: OnClickListener) : RecyclerView.Adapter<HeadlineAdapter.ViewHolder>() {
    private var dataSet: ArrayList<NewsItem>?=null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(newDataSet: ArrayList<NewsItem>){
        dataSet=newDataSet
        notifyDataSetChanged()
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val itemBinding = ItemHeadlineBinding.inflate(LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        Log.d("ViewHolder:", "onCreateViewHolder")
        return ViewHolder(viewGroup.context, itemBinding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val newsItem: NewsItem? = dataSet?.get(position)
        if(newsItem!=null)
        viewHolder.bind(newsItem)
        viewHolder.itemView.setOnClickListener {
            onClickListener.onClick(newsItem!!)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int =
        if(dataSet!=null)
            dataSet!!.size
        else
            0

    class ViewHolder(private val context: Context, private val itemBinding: ItemHeadlineBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(newsItem:NewsItem){
            Log.d("ViewHolder:", "$newsItem")
            itemBinding.tvHeadline.text = newsItem.title
            itemBinding.tvDescription.text = newsItem.description
            //val date = LocalDate.parse(newsItem.publishedAt , DateTimeFormatter.ofPattern("hh:mm aa, DD MMM yyyy"))
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val formatter2 = DateTimeFormatter.ofPattern("dd MMM yyyy    hh:mm a")
            val date = formatter.parse(newsItem.publishedAt)
            itemBinding.tvDateTime.text = formatter2.format(date)
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(context).load(newsItem.urlToImage).placeholder(R.drawable.ic_launcher_foreground).centerCrop().apply(requestOptions).into(itemBinding.ivNewsImage)
        }
    }

    class OnClickListener(val clickListener: (newsItem:NewsItem) -> Unit) {
        fun onClick(newsItem:NewsItem) = clickListener(newsItem)
    }

}
