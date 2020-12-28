package com.example.katalogfilm4.ui.movie.detailmovie

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import com.example.katalogfilm4.MovieCatalogue
import com.example.katalogfilm4.R
import com.example.katalogfilm4.databinding.ActivityDetailMovieBinding
import com.example.katalogfilm4.db.MovieCatalogueDatabasee
import com.example.katalogfilm4.ui.movie.pojo.ResultsItem
import com.google.android.material.snackbar.Snackbar

class DetailMovieActivity : AppCompatActivity() {

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var movieCatalogueDatabasee: MovieCatalogueDatabasee
    private lateinit var movieModel: ResultsItem
    lateinit var binding: ActivityDetailMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        movieCatalogueDatabasee = MovieCatalogueDatabasee.getDatabase(this)
        val viewModel = ViewModelProviders.of(this).get(DetailMovieViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_movie)

        movieModel = intent.getParcelableExtra(SELECTED_MOVIE)
        viewModel.resultsItem = movieModel
        binding.setViewmodel(viewModel)

        Glide.with(this).load("https://image.tmdb.org/t/p/w185/" + movieModel.posterPath!!)
            .into(binding.imgPoster)

        setTitle(viewModel.resultsItem!!.title)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        checkFavorite()

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        val SELECTED_MOVIE = "selected_movie"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        setFavMovieDb()
        return true
    }

    private fun setFavMovieDb() {
        if (isFavorite)
            menuItem?.findItem(R.id.action_favorite)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        else
            menuItem?.findItem(R.id.action_favorite)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp)

    }

    private fun checkFavorite() {
        AsyncTask.execute {
            val moviedb = movieCatalogueDatabasee.movieDao().getMovieById(movieModel.id)
            isFavorite = moviedb?.title != null

            runOnUiThread {
                setFavMovieDb()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_favorite -> {
                if (isFavorite) {
                    AsyncTask.execute {
                        movieCatalogueDatabasee.movieDao().deleteMovieById(movieModel.id)
                        runOnUiThread {
                            isFavorite = !isFavorite
                            setFavMovieDb()
                            Snackbar.make(binding.root, getString(R.string.favorite_success_remove_movie), Snackbar.LENGTH_LONG).show()
                        }
                    }
                }else {
                    AsyncTask.execute {
                        movieCatalogueDatabasee.movieDao().insert(movieModel)
                        runOnUiThread {
                            isFavorite = !isFavorite
                            setFavMovieDb()
                            Snackbar.make(binding.root, getString(R.string.favorite_success_add_movie), Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
