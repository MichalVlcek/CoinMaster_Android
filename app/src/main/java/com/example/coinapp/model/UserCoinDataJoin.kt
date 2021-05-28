package com.example.coinapp.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "user_coin_join",
    primaryKeys = ["userId", "coinId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Coin::class,
            parentColumns = ["id"],
            childColumns = ["coinId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class UserCoinDataJoin(
    var userId: Int = 0,
    var coinId: String = ""
)