package com.example.coinapp.utils

import com.example.coinapp.data.Coin
import java.text.NumberFormat

object StringOperations {

    fun formatCurrency(money: Double): String {
        return NumberFormat.getInstance().format(money) + "$"
    }

    fun formatRank(rank: Int): String {
        return "#$rank"
    }

    fun formatCurrency(money: Double, coin: Coin): String {
        val numberFormat = NumberFormat.getInstance()
        numberFormat.maximumFractionDigits = 10

        return numberFormat.format(money) + " " + coin.symbol
    }

    fun formatPercentage(percentage: Double): String {
        val numberFormat = NumberFormat.getInstance()
        numberFormat.maximumFractionDigits = 2

        return numberFormat.format(percentage * 100) + "%"
    }
}