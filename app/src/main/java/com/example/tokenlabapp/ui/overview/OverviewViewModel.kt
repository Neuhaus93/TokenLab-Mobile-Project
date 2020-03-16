package com.example.tokenlabapp.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tokenlabapp.network.MoviesApi
import com.example.tokenlabapp.network.MoviesProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


enum class MoviesApiStatus { LOADING, ERROR, DONE }
/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<MoviesApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<MoviesApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _properties = MutableLiveData<List<MoviesProperty>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<MoviesProperty>>
        get() = _properties

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<MoviesProperty>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<MoviesProperty>
        get() = _navigateToSelectedProperty

    // Create a Coroutine scope using a job to e able to cancel when needed
    private var viewModelJob = Job()

    // The Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call getMoviesProperties() on init so we can display status immediately.
     */
    init {
        getMoviesProperties()
    }

    /**
     * Gets Mars real estate property information from the Mars API Retrofit service and updates the
     * [MoviesProperty] [List] and [MoviesApiStatus] [LiveData]. The Retrofit service returns a
     * coroutine Deferred, which we await to get the result of the transaction.
     */
    private fun getMoviesProperties() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred = MoviesApi.retrofitService.getProperties()
            try {
                _status.value = MoviesApiStatus.LOADING
                // This will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _status.value = MoviesApiStatus.DONE
                _properties.value = listResult
            } catch (e: Exception) {
                _status.value = MoviesApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param moviesProperty The [MoviesProperty] that was clicked on.
     */
    fun displayMovieDetails(moviesProperty: MoviesProperty) {
        _navigateToSelectedProperty.value = moviesProperty
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

}