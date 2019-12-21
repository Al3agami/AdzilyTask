package com.agamidev.adzilytask.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.agamidev.adzilytask.Api.IMAGE_BASE
import com.agamidev.adzilytask.Api.NetworkStatus
import com.agamidev.adzilytask.Models.Movie
import com.agamidev.adzilytask.R
import com.agamidev.adzilytask.Views.MovieDetailsActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.loading_card_view.view.*
import kotlinx.android.synthetic.main.movie_card_view.view.*

class PopularMoviesAdapter (val context: Context) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallBack()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkStatus: NetworkStatus? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if(viewType == MOVIE_VIEW_TYPE){
            view = layoutInflater.inflate(R.layout.movie_card_view, parent, false)
            return MoviesViewHolder(view)
        }else {
            view = layoutInflater.inflate(R.layout.loading_card_view, parent, false)
            return NetworkStateViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == MOVIE_VIEW_TYPE){
            (holder as MoviesViewHolder).bindViews(getItem(position), context)
        }else {
            (holder as NetworkStateViewHolder).bindViews(networkStatus)
        }
    }

    private fun hasExtraRow(): Boolean{
        return networkStatus != null && networkStatus != NetworkStatus.LOADED //true if Loading, EndOfList or Error
    }

    override fun getItemCount(): Int{
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if(hasExtraRow() && position == itemCount - 1){
            NETWORK_VIEW_TYPE
        }else {
            MOVIE_VIEW_TYPE
        }
    }

    fun setNetworkState(currentNetworkStatus: NetworkStatus){
        val previousStatus = this.networkStatus
        val hadExtraRow = hasExtraRow()
        this.networkStatus = currentNetworkStatus
        val hasExtraRow = hasExtraRow()

        if(hadExtraRow != hasExtraRow){
            if (hadExtraRow){
                notifyItemRemoved(super.getItemCount()) //Remove the ProgressBar
            }else {
                notifyItemInserted(super.getItemCount()) //Add the ProgressBar
            }
        }else if(hasExtraRow && previousStatus != networkStatus){
            notifyItemChanged(itemCount - 1)
        }
    }


    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindViews(movie: Movie?, context: Context){
            itemView.tv_movie_title_cv.text = movie?.original_title
            itemView.tv_movie_rate_cv.text = movie?.vote_average.toString()
            itemView.tv_release_year_cv.text = movie?.release_date!!.split("-")[0] + "/" + movie?.release_date!!.split("-")[1]


            Glide.with(context)
                .load(IMAGE_BASE +movie?.poster_path)
                .centerCrop()
                .placeholder(R.mipmap.ic_image_placeholder)
                .into(itemView.iv_movie_poster_cv)

            itemView.setOnClickListener {
                val i = Intent(context, MovieDetailsActivity::class.java)
                i.putExtra("movie",movie)
                context.startActivity(i)

            }

        }

    }

    class NetworkStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindViews(networkStatus: NetworkStatus?){
            if(networkStatus != null){
                if (networkStatus == NetworkStatus.LOADING){
                    itemView.progress_bar_cv.visibility = View.VISIBLE
                }else {
                    itemView.progress_bar_cv.visibility = View.GONE
                }

                if (networkStatus == NetworkStatus.ERROR || networkStatus == NetworkStatus.END_OF_LIST){
                    itemView.tv_network_error_cv.visibility = View.VISIBLE
                    itemView.tv_network_error_cv.text = networkStatus.message
                }else {
                    itemView.tv_network_error_cv.visibility = View.GONE
                }
            }

        }

    }

    class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

}