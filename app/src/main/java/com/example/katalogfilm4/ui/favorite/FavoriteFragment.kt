package com.example.katalogfilm4.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.katalogfilm4.R
import com.example.katalogfilm4.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        binding.viewmodel = favoriteViewModel
        binding.viewPager.adapter = SectionPagerAdapter(binding.root.context, childFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)

        return binding.root
    }
}