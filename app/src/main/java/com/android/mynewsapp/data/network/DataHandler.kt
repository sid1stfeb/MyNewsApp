package com.android.mynewsapp.data.network

data class DataHandler<out T>(
    val status: Status,
    val data: T?,
    val message:String?
){
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
    companion object{

    fun <T> success(data:T?): DataHandler<T>{
        return DataHandler(Status.SUCCESS, data, null)
    }

    fun <T> error(msg:String, data:T?): DataHandler<T>{
        return DataHandler(Status.ERROR, data, msg)
    }

    fun <T> loading(data:T?): DataHandler<T>{
        return DataHandler(Status.LOADING, data, null)
    }

}
}
