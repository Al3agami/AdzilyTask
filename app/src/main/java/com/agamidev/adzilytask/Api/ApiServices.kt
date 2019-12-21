package com.agamidev.adzilytask.Api

import com.agamidev.adzilytask.Models.PopularMoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServices {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Single<PopularMoviesResponse>
}