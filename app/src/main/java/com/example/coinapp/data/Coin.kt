package com.example.coinapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coin(
    val icon: String,
    val rank: Int,
    val name: String,
    var price: Double,
    var marketCap: Double,
    var supply: Double
) : Parcelable
