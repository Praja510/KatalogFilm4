package com.example.katalogfilm4.ui.movie.favmovies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.katalogfilm4.R
import com.example.katalogfilm4.databinding.FavMoviesFragmentBinding
import com.example.katalogfilm4.ui.movie.MoviesAdapter
import com.example.katalogfilm4.ui.movie.detailmovie.DetailMovieActivity
import com.example.katalogfilm4.ui.movie.pojo.ResultsItem

class FavMoviesFragment : Fragment() {

    private lateinit var favMoviesFragmentBinding: FavMoviesFragmentBinding
    private lateinit var mViewModel: FavMoviesViewModel
    private lateinit var alertDialog: AlertDialog

    private val getFavMovie = Observer<List<ResultsItem>> {
        val mAdapter = MoviesAdapter(it)
        if (it.size > 0) {
            favMoviesFragmentBinding.tvMessage.visibility = View.GONE
            mAdapter.SetOnItemClickListener(object : MoviesAdapter.OnItemClickListener {
                override fun onItemClick(view: View, model: ResultsItem) {
                    val goToDetailMovie = Intent(view.context, DetailMovieActivity::class.java)
                    goToDetailMovie.putExtra(DetailMovieActivity.SELECTED_MOVIE, model)
                    startActivity(goToDetailMovie)
                }
            })
            favMoviesFragmentBinding.recyclerView.adapter = mAdapter
        } else {
            favMoviesFragmentBinding.recyclerView.adapter = null
            favMoviesFragmentBinding.tvMessage.visibility = View.VISIBLE
        }

        favMoviesFragmentBinding.progressBar.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favMoviesFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.fav_movies_fragment, container, false)
        mViewModel = ViewModelProviders.of(this).get(FavMoviesViewModel::class.java)
        return favMoviesFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialog = AlertDialog.Builder(view.context).setTitle(getString(R.string.failure))
            .setPositiveButton("OK") { dialog, which -> }.create()

        val layoutManager = LinearLayoutManager(view.context)
        favMoviesFragmentBinding.recyclerView.layoutManager = layoutManager
        favMoviesFragmentBinding.recyclerView.setHasFixedSize(true)
        favMoviesFragmentBinding.viewmodel = mViewModel
    }

    override fun onResume() {
        super.onResume()
        mViewModel.fetchFavMovies()
        mViewModel.movies.observe(this, getFavMovie)
    }
}
