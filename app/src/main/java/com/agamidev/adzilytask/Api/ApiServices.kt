package com.agamidev.adzilytask.Api

import com.agamidev.adzilytask.Models.Movie
import com.agamidev.adzilytask.Models.PopularMoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiServices {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int
    ): Single<PopularMoviesResponse>
}