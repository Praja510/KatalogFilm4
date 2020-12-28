package com.example.katalogfilm4.ui.tvshow.detailtvshow

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.katalogfilm4.R
import com.example.katalogfilm4.databinding.ActivityDetailTvShowsBinding
import com.example.katalogfilm4.db.MovieCatalogueDatabasee
import com.example.katalogfilm4.ui.tvshow.pojo.ResultsItem
import com.google.android.material.snackbar.Snackbar

class DetailTvShowsActivity : AppCompatActivity() {

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var movieCatalogueDatabasee: MovieCatalogueDatabasee
    private lateinit var tvShowModel: ResultsItem
    private lateinit var binding: ActivityDetailTvShowsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        movieCatalogueDatabasee = MovieCatalogueDatabasee.getDatabase(this)
        val viewModel = ViewModelProviders.of(this)
            .get<DetailTvShowsViewModel>(DetailTvShowsViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_tv_shows)

        tvShowModel = intent.getParcelableExtra(SELECTED_TV_SHOWS)
        viewModel.resultsItem = tvShowModel
        binding.viewmodel = viewModel

        Glide.with(this).load("https://image.tmdb.org/t/p/w185/" + tvShowModel.posterPath!!)
            .into(binding.imgPoster)

        title = viewModel.resultsItem!!.name

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        checkFavorite()

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        val SELECTED_TV_SHOWS = "selected_tv_shows"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        setFavTvShowDb()
        return true
    }

    private fun setFavTvShowDb() {
        if (isFavorite)
            menuItem?.findItem(R.id.action_favorite)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        else
            menuItem?.findItem(R.id.action_favorite)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp)

    }

    private fun checkFavorite() {
        AsyncTask.execute {
            val tvShowDb = movieCatalogueDatabasee.tvShowDao().getTvShowById(tvShowModel.id)
            isFavorite = tvShowDb?.name != null

            runOnUiThread {
                setFavTvShowDb()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_favorite -> {
                if (isFavorite) {
                    AsyncTask.execute {
                        movieCatalogueDatabasee.tvShowDao().deleteTvShowById(tvShowModel.id)
                        runOnUiThread {
                            isFavorite = !isFavorite
                            setFavTvShowDb()
                            Snackbar.make(
                                binding.root,
                                getString(R.string.favorite_success_add_tv_show),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    AsyncTask.execute {
                        movieCatalogueDatabasee.tvShowDao().insert(tvShowModel)
                        runOnUiThread {
                            isFavorite = !isFavorite
                            setFavTvShowDb()
                            Snackbar.make(
                                binding.root,
                                getString(R.string.favorite_success_add_tv_show),
                                Snackbar.LENGTH_LONG
                            ).show()

                        }
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
