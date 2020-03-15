package com.example.tokenlabapp.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tokenlabapp.network.MoviesProperty


/**
 * Simple ViewModel factory that provides the MoviesProperty and context to the ViewModel.
 */
class DetailViewModelFactory(
    private val movieProperty: MoviesProperty,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(movieProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}