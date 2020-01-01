package com.zestworks.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zestworks.data.model.Movie
import com.zestworks.movielist.databinding.ListMovieItemBinding

class MovieListAdapter(
    private var movies: List<Movie>,
    private val itemClickCallback: (Int) -> Unit
) :
    RecyclerView.Adapter<MovieListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieListViewHolder(inflater.inflate(R.layout.list_movie_item, parent, false))
    }

    override fun getItemCount(): Int = movies.count()

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.apply {
            textMovieName.text = movie.title
            textReleaseDate.text = movie.releaseDate
            // TODO : Move this to a "UiModel" so as to test this logic as this URL can potentially vary in different build flavors.
            Glide.with(holder.itemView.context)
                .load("https://image.tmdb.org/t/p/w780${movie.posterPath}").into(imagePoster)
            root.setOnClickListener {
                itemClickCallback(movies[holder.adapterPosition].id)
            }
        }
    }

    fun setData(data: List<Movie>) {
        movies = data
    }
}

class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: ListMovieItemBinding = ListMovieItemBinding.bind(itemView)
}
