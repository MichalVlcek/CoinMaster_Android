package com.example.coinapp.utils

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.example.coinapp.R
import com.example.coinapp.model.User
import com.example.coinapp.model.enums.TransactionType


object TextViewOperations {
    private const val red = R.color.red
    private const val green = R.color.green
    private const val blue = R.color.lightBlue
    private const val orange = R.color.orange

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

    fun TextView.setTextAndColor(user: User, fragmentContext: Context) {
        this.text = fragmentContext.getString(
            if (user.premium)
                R.string.premium_user
            else
                R.string.basic_user
        )

        this.setTextColor(
            when (user.premium) {
                true -> getColor(context, orange)
                false -> getColor(context, blue)
            }
        )
    }
}