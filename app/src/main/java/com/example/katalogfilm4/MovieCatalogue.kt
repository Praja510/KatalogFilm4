package com.example.katalogfilm4

import android.app.Application
import android.content.Context
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import java.util.*

class MovieCatalogue : Application () {

    lateinit var context: Context
    override fun onCreate() {
        super.onCreate()
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                    massage -> Log.d("API", massage)
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        AndroidNetworking.initialize(applicationContext, okHttpClient)

        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
        context = applicationContext
    }

    companion object {
        const val MOVIE_DB_API_KEY = "43e57b623a9ad4ee4c6f1d69f89a0646"
    }

    fun getLanguange():String {
        if (Locale.getDefault().language.toLowerCase().equals("in")) {
            return "id"
        }else{
            return  "en-US"
        }
    }
}