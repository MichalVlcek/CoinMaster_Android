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
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Coin::class,
            parentColumns = ["id"],
            childColumns = ["coinId"],
            onDelete = ForeignKey.NO_ACTION
        )]
)
data class UserCoinDataJoin(
    var userId: Long = 0,
    var coinId: String = ""
)