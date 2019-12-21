package com.agamidev.adzilytask.Repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.agamidev.adzilytask.Api.ApiServices
import com.agamidev.adzilytask.Api.NetworkStatus
import com.agamidev.adzilytask.Api.PAGE_SIZE
import com.agamidev.adzilytask.Data.PopularMoviesDataSource
import com.agamidev.adzilytask.Data.PopularMoviesDataSourceFactory
import com.agamidev.adzilytask.Models.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviesPagedListRepo(private val apiServices: ApiServices) {

    lateinit var moviesDataSourceFactory: PopularMoviesDataSourceFactory
    lateinit var moviesPagedList: LiveData<PagedList<Movie>>

    fun fetchPopularMovies(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>>{

        moviesDataSourceFactory =
            PopularMoviesDataSourceFactory(
                apiServices,
                compositeDisposable
            )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()

        moviesPagedList = LivePagedListBuilder(moviesDataSourceFactory,config).build()

        return moviesPagedList

    }

    fun getNetworkState(): LiveData<NetworkStatus>{
        return Transformations.switchMap<PopularMoviesDataSource, NetworkStatus>(
            moviesDataSourceFactory.popularMovie_LiveDataSource,
            PopularMoviesDataSource::networkStatus
        )
    }

}