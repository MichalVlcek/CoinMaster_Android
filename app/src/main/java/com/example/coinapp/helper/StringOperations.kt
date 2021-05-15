package com.example.coinapp.helper

import com.example.coinapp.data.Coin
import java.text.NumberFormat

object StringOperations {

    fun formatCurrency(money: Double): String {
        return NumberFormat.getInstance().format(money) + "$"
    }

    fun formatCurrency(money: Double, coin: Coin): String {
        val numberFormat = NumberFormat.getInstance()
        numberFormat.maximumFractionDigits = 10

        return numberFormat.format(money) + " " + coin.symbol
    }

    fun formatPercentage(percentage: Double): String {
        val numberFormat = NumberFormat.getInstance()
        numberFormat.maximumFractionDigits = 2

        return numberFormat.format((percentage - 1) * 100) + "%"
    }
}