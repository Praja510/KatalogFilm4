package com.example.katalogfilm4.ui.movie.favmovies

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.katalogfilm4.db.MovieCatalogueDatabasee
import com.example.katalogfilm4.ui.movie.pojo.ResultsItem

class FavMoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val favMovies = MutableLiveData<MutableList<ResultsItem>>()
    private var movieCatalogueDatabasee = MovieCatalogueDatabasee.getDatabase(getApplication())

    val movies: MutableLiveData<MutableList<ResultsItem>>
        get() {
            return favMovies
        }

    fun fetchFavMovies() {
        AsyncTask.execute {
            favMovies.postValue(movieCatalogueDatabasee.movieDao().getAllMovie())
        }
    }

}