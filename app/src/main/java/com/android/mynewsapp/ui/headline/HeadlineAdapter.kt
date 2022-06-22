package com.android.mynewsapp.ui.headline

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mynewsapp.data.model.NewsItem
import com.android.mynewsapp.databinding.ItemHeadlineBinding
import com.android.mynewsapp.other.Util.getReadableDate
import com.android.mynewsapp.other.Util.loadImage

class HeadlineAdapter (private val onClickListener: OnClickListener) : RecyclerView.Adapter<HeadlineAdapter.ViewHolder>() {
    private var dataSet: ArrayList<NewsItem> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newDataSet: ArrayList<NewsItem>){
        dataSet.addAll(newDataSet)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData(){
        dataSet.clear()
        notifyDataSetChanged()
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemHeadlineBinding.inflate(LayoutInflater.from(viewGroup.context),
            viewGroup, false)
        Log.d("ViewHolder:", "onCreateViewHolder")
        return ViewHolder(viewGroup.context, itemBinding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val newsItem: NewsItem = dataSet[position]
        viewHolder.bind(newsItem)
        viewHolder.itemView.setOnClickListener {
            onClickListener.onClick(newsItem)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int =
        dataSet.size

    class ViewHolder(private val context: Context, private val itemBinding: ItemHeadlineBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(newsItem:NewsItem){
            Log.d("ViewHolder:", "$newsItem")
            itemBinding.tvHeadline.text = newsItem.title
            itemBinding.tvDescription.text = newsItem.description
            itemBinding.tvDateTime.text = getReadableDate(newsItem.publishedAt)
            loadImage(context, newsItem.urlToImage, itemBinding.ivNewsImage)
        }
    }

    class OnClickListener(val clickListener: (newsItem:NewsItem) -> Unit) {
        fun onClick(newsItem:NewsItem) = clickListener(newsItem)
    }

}
