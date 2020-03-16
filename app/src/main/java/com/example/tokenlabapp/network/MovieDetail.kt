package com.example.tokenlabapp.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetail (
    val budget: Double
) : Parcelable