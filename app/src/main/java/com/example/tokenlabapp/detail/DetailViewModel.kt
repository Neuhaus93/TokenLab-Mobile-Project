package com.example.tokenlabapp.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.tokenlabapp.network.MoviesProperty


/**
 *  The [ViewModel] associated with the [DetailFragment], containing information about the selected
 *  [MoviesProperty].
 */
class DetailViewModel(moviesProperty: MoviesProperty, app: Application) : AndroidViewModel(app) {
    private val _selectedMovie = MutableLiveData<MoviesProperty>()

    // The external LiveData for the SelectedProperty
    val selectedMovie: LiveData<MoviesProperty>
        get() = _selectedMovie

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedMovie.value = moviesProperty
    }

    // The displayPropertyPrice formatted Transformation Map LiveData, which displays the sale
    // or rental price.
    val displayMovieTitle = Transformations.map(selectedMovie) {
        it.title
    }

    val displayMovieReleaseDate = Transformations.map(selectedMovie) {
        it.release_date
    }
}