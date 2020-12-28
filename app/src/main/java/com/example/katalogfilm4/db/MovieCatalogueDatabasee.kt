package com.example.katalogfilm4.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.example.katalogfilm4.ui.movie.MovieDao
import com.example.katalogfilm4.ui.movie.pojo.ResultsItem
import com.example.katalogfilm4.ui.tvshow.TvShowDao


@Database(
    entities = [ResultsItem::class, com.example.katalogfilm4.ui.tvshow.pojo.ResultsItem::class],
    version = 1,
    exportSchema = false
)
abstract class MovieCatalogueDatabasee : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

    companion object {
        const val DB_NAME = "katalog_film_database"

        @Volatile
        private var INSTANCE: MovieCatalogueDatabasee? = null

        fun getDatabase(context: Context): MovieCatalogueDatabasee {
            if (INSTANCE == null) {
                synchronized(MovieCatalogueDatabasee::class.java) {
                    if (INSTANCE == null) {

                        INSTANCE = Room.databaseBuilder(
                            context,
                            MovieCatalogueDatabasee::class.java, DB_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}