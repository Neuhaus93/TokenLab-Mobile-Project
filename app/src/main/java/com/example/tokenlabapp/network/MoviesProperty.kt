package com.example.tokenlabapp.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesProperty (
    val id: Double,
    val vote_average: Double,
    val title: String,
    @Json(name = "poster_url") val imgSrcUrl: String,
    val release_date: String
) : Parcelable