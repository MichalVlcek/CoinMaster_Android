package com.example.coinapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Coin(
    @PrimaryKey val id: String,
    val icon: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    var price: Double,
    @ColumnInfo(name = "market_cap") var marketCap: Double,
    var supply: Double
) : Parcelable
