package com.agamidev.adzilytask.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.agamidev.adzilytask.Adapters.PopularMoviesAdapter
import com.agamidev.adzilytask.Api.NetworkStatus
import com.agamidev.adzilytask.Api.RetrofitClient
import com.agamidev.adzilytask.R
import com.agamidev.adzilytask.Repos.MoviesPagedListRepo
import com.agamidev.adzilytask.ViewModels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    lateinit var moviesRepo: MoviesPagedListRepo

    lateinit var moviesAdapter: PopularMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesRepo = MoviesPagedListRepo(RetrofitClient.client)

        viewModel = getViewModel()

        moviesAdapter = PopularMoviesAdapter(this)


        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                if(moviesAdapter.getItemViewType(position) == moviesAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3
            }

        }

        rv_popular_movies.layoutManager = gridLayoutManager
        rv_popular_movies.setHasFixedSize(true)
        rv_popular_movies.adapter = moviesAdapter

        viewModel.moviesPagedList.observe(this, Observer {
            moviesAdapter.submitList(it)
            moviesAdapter.notifyDataSetChanged()
        })

        viewModel.networkStatus.observe(this, Observer {
            progress_bar_main.visibility = if(it == NetworkStatus.LOADING && viewModel.isEmpty()) View.VISIBLE else View.GONE
            tv_network_error_main.visibility = if(it == NetworkStatus.ERROR && viewModel.isEmpty()) View.VISIBLE else View.GONE

            if(!viewModel.isEmpty()){
                moviesAdapter.setNetworkState(it)
            }
        })



    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainActivityViewModel(
                    moviesRepo
                ) as T
            }
        })[MainActivityViewModel::class.java]
    }

}
