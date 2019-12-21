package com.agamidev.adzilytask.Data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.agamidev.adzilytask.Api.ApiServices
import com.agamidev.adzilytask.Models.Movie
import io.reactivex.disposables.CompositeDisposable

class PopularMoviesDataSourceFactory (private val apiServices: ApiServices, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>(){

    val popularMovie_LiveDataSource = MutableLiveData<PopularMoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val popularMoviesDataSource =
            PopularMoviesDataSource(
                apiServices,
                compositeDisposable
            )

        popularMovie_LiveDataSource.postValue(popularMoviesDataSource)
        return popularMoviesDataSource

    }
}