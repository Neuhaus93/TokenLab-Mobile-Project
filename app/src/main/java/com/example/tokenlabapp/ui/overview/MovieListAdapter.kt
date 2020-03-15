package com.example.tokenlabapp.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tokenlabapp.databinding.GridViewItemBinding
import com.example.tokenlabapp.network.MoviesProperty


/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 * @param onClick a lambda that takes the
 */
class MovieListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<MoviesProperty, MovieListAdapter.MoviePropertyViewHolder>(DiffCallback) {
    /**
     * The MarsPropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [MoviesProperty] information.
     */
    class MoviePropertyViewHolder(private val binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(moviesProperty: MoviesProperty) {
            binding.property = moviesProperty
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MoviesProperty>() {
        override fun areItemsTheSame(
            oldItem: MoviesProperty,
            newItem: MoviesProperty
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: MoviesProperty,
            newItem: MoviesProperty
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePropertyViewHolder {
        return MoviePropertyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MoviePropertyViewHolder, position: Int) {
        val movieProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movieProperty)
        }
        holder.bind(movieProperty)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [MoviesProperty]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [MoviesProperty]
     */
    class OnClickListener(val clickListener: (moviesProperty: MoviesProperty) -> Unit) {
        fun onClick(moviesProperty: MoviesProperty) = clickListener(moviesProperty)
    }
}