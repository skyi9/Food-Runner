package com.learn.foodrunner.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

//check connectivity of the device
class ConnectionManager {
    fun isNetworkAvailable(context: Context) : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting)
    }
}