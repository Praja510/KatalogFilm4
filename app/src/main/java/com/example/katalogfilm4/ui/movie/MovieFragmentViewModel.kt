package com.example.katalogfilm4.ui.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.katalogfilm4.MovieCatalogue
import com.example.katalogfilm4.ui.movie.pojo.ResponseMovies

class MovieFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val responseMovies = MutableLiveData<ResponseMovies>()

    val movies: MutableLiveData<ResponseMovies>
        get() {
            return responseMovies
        }

    fun doRequestListMovies() {
        AndroidNetworking.get("https://api.themoviedb.org/3/discover/movie")
            .addQueryParameter("api_key", MovieCatalogue.MOVIE_DB_API_KEY)
            .addQueryParameter("languange", (getApplication() as MovieCatalogue).getLanguange())
            .build()
            .getAsObject(ResponseMovies::class.java, object : ParsedRequestListener<ResponseMovies> {
                override fun onResponse(response: ResponseMovies?) {
                    responseMovies.postValue(response)
                }

                override fun onError(anError: ANError?) {
                    responseMovies.value = ResponseMovies(anError)
                }

            })

    }
}