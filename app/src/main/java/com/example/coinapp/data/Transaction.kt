package com.example.coinapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Transaction(
    val type: TransactionType,
    val date: Date,
    val cost: Double,
    val amount: Double
) : Parcelable