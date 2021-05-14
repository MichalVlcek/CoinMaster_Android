package com.example.coinapp.helper

import java.text.NumberFormat

object StringOperations {
    fun formatCurrency(money: Double): String {
        return NumberFormat.getInstance().format(money) + "$"
    }
}