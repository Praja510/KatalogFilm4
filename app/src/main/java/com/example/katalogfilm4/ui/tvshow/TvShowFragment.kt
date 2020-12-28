package com.example.katalogfilm4.ui.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.katalogfilm4.R
import com.example.katalogfilm4.databinding.FragmentTvShowBinding
import com.example.katalogfilm4.ui.tvshow.detailtvshow.DetailTvShowsActivity
import com.example.katalogfilm4.ui.tvshow.pojo.ResponseTvShows
import java.util.*

class TvShowFragment : Fragment() {

    lateinit var alertDialog: AlertDialog
    lateinit var binding: FragmentTvShowBinding
    lateinit var mViewModel: TvShowViewModel

    val getTvshows = Observer<ResponseTvShows> { responseTvShows ->
        if (responseTvShows != null) {
            if (responseTvShows.anError == null ) {
                val mAdapter = TvShowsAdapter(responseTvShows.results!!)

                mAdapter.SetOnItemClickListener(object : TvShowsAdapter.OnItemClickListener {
                    override fun onItemClick(
                        view: View,
                        model: com.example.katalogfilm4.ui.tvshow.pojo.ResultsItem
                    ) {
                        val goToDetailMovie =
                            Intent(view.context, DetailTvShowsActivity::class.java)
                        goToDetailMovie.putExtra(DetailTvShowsActivity.SELECTED_TV_SHOWS, model)
                        startActivity(goToDetailMovie)
                    }
                })
                binding.recyclerView.adapter = mAdapter
            }else{
                alertDialog.setMessage(responseTvShows.anError.message)
                alertDialog.show()
            }
        }

        binding.progressBar.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_tv_show,container,false)
        mViewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialog = AlertDialog.Builder(view.context).setTitle(getString(R.string.failure)).setPositiveButton("OK") {dialog, which -> }.create()

        val layoutManager = LinearLayoutManager(view.context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        mViewModel.doRequestListTvShows()
        mViewModel.getTvShowList.observe(this, getTvshows)
    }
}
