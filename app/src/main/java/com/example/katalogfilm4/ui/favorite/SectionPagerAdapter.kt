package com.example.katalogfilm4.ui.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.example.katalogfilm4.R
import com.example.katalogfilm4.ui.movie.favmovies.FavMoviesFragment
import com.example.katalogfilm4.ui.tvshow.favtvshow.FavTvShowFragment



/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
internal class SectionPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_movies, R.string.tab_tv_shows)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    private val pages = listOf(
        FavMoviesFragment(),
        FavTvShowFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }
}