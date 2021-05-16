package com.example.coinapp.helper

import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.example.coinapp.R
import com.example.coinapp.data.TransactionType

const val red = R.color.red
const val green = R.color.green
const val blue = R.color.lightBlue

object TextViewOperations {
    fun TextView.setTextAndColor(text: String, value: Double) {
        this.text = text

        if (value == 0.0) {
            this.setTextColor(getColor(context, blue))
        }
        if (value > 0) {
            this.setTextColor(getColor(context, green))
        } else {
            this.setTextColor(getColor(context, red))
        }
    }

    fun TextView.setTextAndColor(transactionType: TransactionType) {
        this.text = transactionType.toString()

        this.setTextColor(
            when (transactionType) {
                TransactionType.BUY -> getColor(context, green)
                TransactionType.SELL -> getColor(context, red)
                else -> getColor(context, blue)
            }
        )
    }
}