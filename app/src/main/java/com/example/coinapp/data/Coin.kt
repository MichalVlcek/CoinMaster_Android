package com.example.coinapp.data

data class Coin(
    val icon: String,
    val rank: Int,
    val name: String,
    var price: Double,
    var marketCap: Double,
)
