package com.example.katalogfilm4.ui.tvshow

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.katalogfilm4.ui.tvshow.pojo.ResultsItem

@Dao
interface TvShowDao {
    companion object{
        const val TABLE_NAME:String = "table_fav_tv_shows"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(resultsItem: ResultsItem)

    @Query("SELECT * FROM ${TABLE_NAME}")
    fun getAllTvShow():MutableList<ResultsItem>

    @Query("DELETE FROM ${TABLE_NAME}")
    fun deleteAll()

    @Query("SELECT * FROM ${TABLE_NAME}")
    fun getTvShowById():List<ResultsItem>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE id = :id LIMIT 1")
    fun getTvShowById(id:Int): ResultsItem?

    @Query("DELETE FROM ${TABLE_NAME} WHERE id = :id")
    fun deleteTvShowById(id:Int)
}