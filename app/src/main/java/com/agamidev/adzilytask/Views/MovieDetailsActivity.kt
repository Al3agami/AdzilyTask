package com.agamidev.adzilytask.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.agamidev.adzilytask.Api.IMAGE_BASE
import com.agamidev.adzilytask.Models.Movie
import com.agamidev.adzilytask.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {


    private val TAG = "MovieDetailsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movie = intent.getParcelableExtra<Movie>("movie")

        fillView(movie)

    }


    fun fillView(movie: Movie){
        tv_movie_title.text = movie.original_title
        movie_overview.text = movie.overview
        tv_release_date.text = movie.release_date
        tv_users_rate.text = movie.vote_average.toString() + " (" + movie.vote_count.toString() + " votes)"

        Glide.with(this)
            .load(IMAGE_BASE+movie.poster_path)
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(iv_movie_poster)
    }
}
