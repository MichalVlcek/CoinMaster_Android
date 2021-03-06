package com.example.coinapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val email: String,
    val password: String,
    val premium: Boolean
) : Parcelable