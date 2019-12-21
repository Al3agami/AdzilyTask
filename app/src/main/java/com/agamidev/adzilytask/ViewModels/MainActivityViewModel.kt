package com.agamidev.adzilytask.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.agamidev.adzilytask.Api.NetworkStatus
import com.agamidev.adzilytask.Models.Movie
import com.agamidev.adzilytask.Repos.MoviesPagedListRepo
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val moviesPagedListRepo: MoviesPagedListRepo):ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviesPagedList: LiveData<PagedList<Movie>> by lazy {
        moviesPagedListRepo.fetchPopularMovies(compositeDisposable)
    }

    val networkStatus: LiveData<NetworkStatus> by lazy {
        moviesPagedListRepo.getNetworkState()
    }

    fun isEmpty(): Boolean{
        return moviesPagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}