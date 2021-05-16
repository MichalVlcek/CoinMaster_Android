package com.example.coinapp.data

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.coinapp.api.ApiService
import com.example.coinapp.db.CoinDatabase

class CoinRepository(context: Context) {

    private val coinDao = CoinDatabase.getInstance(context).coinDao()

    @WorkerThread
    suspend fun insertCoin(coin: Coin) {
        coinDao.insertAll(coin)
    }

    @WorkerThread
    suspend fun loadAllCoins(): List<Coin> {
        var coins: List<Coin> = emptyList()
        try {
            coins = ApiService.getInstance().getCoins()

            coinDao.insertAll(*coins.toTypedArray())
        } catch (e: Exception) {
            coins = coinDao.getAll()
        }

        return coins
    }
}