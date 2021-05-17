package com.example.coinapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "coin_id") val coinId: String?,
    val type: TransactionType,
    val date: LocalDate,
    val cost: Double,
    val amount: Double,
    val fee: Double,
    @ColumnInfo(name = "fee_type") val feeType: FeeType,
    val description: String = ""
) : Parcelable