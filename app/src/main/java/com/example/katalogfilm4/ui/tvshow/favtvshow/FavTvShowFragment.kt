package com.example.katalogfilm4.ui.tvshow.favtvshow

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
import com.example.katalogfilm4.databinding.FavTvShowFragmentBinding
import com.example.katalogfilm4.ui.tvshow.TvShowsAdapter
import com.example.katalogfilm4.ui.tvshow.detailtvshow.DetailTvShowsActivity
import com.example.katalogfilm4.ui.tvshow.pojo.ResultsItem

class FavTvShowFragment : Fragment() {

    private lateinit var favTvShowFragmentBinding: FavTvShowFragmentBinding
    private lateinit var mViewModel: FavTvShowViewModel
    private lateinit var alertDialog: AlertDialog

    val getTvShow = Observer<List<ResultsItem>> {
        val mAdapter = TvShowsAdapter(it)
        if (it.size > 0) {
            favTvShowFragmentBinding.tvMessage.visibility = View.GONE

            mAdapter.SetOnItemClickListener(object : TvShowsAdapter.OnItemClickListener {
                override fun onItemClick(view: View, model: ResultsItem) {
                    val goToDetailMovie = Intent(view.context, DetailTvShowsActivity::class.java)
                    goToDetailMovie.putExtra(DetailTvShowsActivity.SELECTED_TV_SHOWS, model)
                    startActivity(goToDetailMovie)
                }

            })

            favTvShowFragmentBinding.recyclerView.adapter = mAdapter
        } else {
            favTvShowFragmentBinding.recyclerView.adapter = null
            favTvShowFragmentBinding.tvMessage.visibility = View.VISIBLE
        }

        favTvShowFragmentBinding.progressBar.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favTvShowFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.fav_tv_show_fragment, container, false)
        mViewModel = ViewModelProviders.of(this).get(FavTvShowViewModel::class.java)
        favTvShowFragmentBinding.viewmodel = mViewModel

        return favTvShowFragmentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialog = AlertDialog.Builder(view.context).setTitle(getString(R.string.failure))
            .setPositiveButton("OK") { dialog, which -> }.create()

        val layoutManager = LinearLayoutManager(view.context)
        favTvShowFragmentBinding.recyclerView.layoutManager = layoutManager
        favTvShowFragmentBinding.recyclerView.setHasFixedSize(true)
        favTvShowFragmentBinding.viewmodel = mViewModel
    }

    override fun onResume() {
        super.onResume()
        mViewModel.fetchFavTvShows()
        mViewModel.getTvShowList.observe(this, getTvShow)
    }
}
