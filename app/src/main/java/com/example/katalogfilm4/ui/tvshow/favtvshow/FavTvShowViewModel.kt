package com.example.katalogfilm4.ui.tvshow.favtvshow

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.katalogfilm4.db.MovieCatalogueDatabasee
import com.example.katalogfilm4.ui.tvshow.pojo.ResultsItem

class FavTvShowViewModel(application: Application) : AndroidViewModel(application) {

    private val favTvShow = MutableLiveData<MutableList<ResultsItem>>()
    private val movieCatalogueDatabasee : MovieCatalogueDatabasee = MovieCatalogueDatabasee.getDatabase(getApplication())

    internal val getTvShowList : MutableLiveData<MutableList<ResultsItem>>
    get() {
        return favTvShow
    }

    internal fun fetchFavTvShows() {
        AsyncTask.execute {
            favTvShow.postValue(movieCatalogueDatabasee.tvShowDao().getAllTvShow())
        }
    }
}