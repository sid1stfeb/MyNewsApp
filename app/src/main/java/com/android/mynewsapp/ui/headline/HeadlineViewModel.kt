package com.android.mynewsapp.ui.headline

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mynewsapp.data.model.NewsList
import com.android.mynewsapp.data.network.ApiHelper
import com.android.mynewsapp.data.network.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadlineViewModel @Inject constructor(private val apiHelper:ApiHelper): ViewModel(){

    private val _headlines = MutableLiveData<DataHandler<NewsList>>()

    val headlines : LiveData<DataHandler<NewsList>> = _headlines

    fun getNews(page:Int) {
        Log.d("HeadlineViewModel:", "getNews" )
        _headlines.postValue(DataHandler.loading(null))
        viewModelScope.launch {
                apiHelper.getNews(page).let {
                if (it.isSuccessful){
                    Log.d("HeadlineViewModel Network Success Response:", "Data: ${it.body()}" )
                    _headlines.postValue(DataHandler.success(it.body()))
                }else{
                    Log.d("HeadlineViewModel Network Failure Response:", "Data: ${it.errorBody().toString()}" )
                    _headlines.postValue(DataHandler.error(it.errorBody().toString(), null))
                }
            }
        }
    }
}