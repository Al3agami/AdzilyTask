package com.agamidev.adzilytask.Data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.agamidev.adzilytask.Api.API_KEY
import com.agamidev.adzilytask.Api.ApiServices
import com.agamidev.adzilytask.Api.FIRST_PAGE
import com.agamidev.adzilytask.Api.NetworkStatus
import com.agamidev.adzilytask.Models.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PopularMoviesDataSource (private val apiServices: ApiServices, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, Movie>(){

    private val TAG = "PopularMoviesDataSource"

    private var page = FIRST_PAGE

    val networkStatus: MutableLiveData<NetworkStatus> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>,callback: LoadInitialCallback<Int, Movie>) {

        networkStatus.postValue(NetworkStatus.LOADING)
        compositeDisposable.add(
            apiServices.getPopularMovies(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.e(TAG,"response_ok")
                        callback.onResult(it.moviesList, null, page+1)
                        networkStatus.postValue(NetworkStatus.LOADED)

                    },
                    {
                        networkStatus.postValue(NetworkStatus.ERROR)
                        Log.e(TAG,it.message!!)
                    }
                )
        )

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkStatus.postValue(NetworkStatus.LOADING)

        compositeDisposable.add(
            apiServices.getPopularMovies(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.total_pages >= params.key){
                            callback.onResult(it.moviesList, params.key + 1)
                            networkStatus.postValue(NetworkStatus.LOADED)
                        }else {
                            networkStatus.postValue(NetworkStatus.END_OF_LIST)
                        }

                    },
                    {
                        networkStatus.postValue(NetworkStatus.ERROR)
                        Log.e(TAG,it.message!!)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }


}