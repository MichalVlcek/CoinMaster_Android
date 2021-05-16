package com.example.coinapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Transaction(
    val coin: Coin?, //TODO shouldn't be null probably
    val type: TransactionType,
    val date: LocalDate,
    val cost: Double,
    val amount: Double,
    val fee: Double,
    val feeType: FeeType,
    val description: String = ""
) : Parcelable