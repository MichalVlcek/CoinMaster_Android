package com.example.coinapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.coinapp.model.enums.FeeType
import com.example.coinapp.model.enums.TransactionType
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Coin::class,
            parentColumns = ["id"],
            childColumns = ["coin_id"],
            onDelete = ForeignKey.NO_ACTION
        )]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "coin_id") val coinId: String?,
    val type: TransactionType,
    val date: LocalDate,
    val cost: Double,
    val amount: Double,
    val fee: Double,
    @ColumnInfo(name = "fee_type") val feeType: FeeType,
    val description: String = ""
) : Parcelable