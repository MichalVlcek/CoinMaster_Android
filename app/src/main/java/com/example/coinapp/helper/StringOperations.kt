package com.example.coinapp.helper

import com.example.coinapp.data.Coin
import java.text.NumberFormat

object StringOperations {
    fun formatCurrency(money: Double): String {
        return NumberFormat.getInstance().format(money) + "$"
    }

    fun formatCurrency(money: Double, coin: Coin): String {
        return NumberFormat.getInstance().format(money) + " " + coin.symbol
    }
}