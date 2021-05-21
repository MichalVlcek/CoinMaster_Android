package com.example.coinapp.data

import android.content.Context
import com.example.coinapp.db.CoinDatabase

class UserRepository(context: Context) {
    companion object {
        private var instance: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            if (instance == null) {
                instance = UserRepository(context)
            }
            return instance!!
        }
    }

    private val transactionDao = CoinDatabase.getInstance(context).transactionDao()

}