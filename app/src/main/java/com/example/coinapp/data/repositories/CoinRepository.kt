package com.example.coinapp.data.repositories

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.coinapp.api.ApiService
import com.example.coinapp.data.Coin
import com.example.coinapp.db.CoinDatabase

class CoinRepository private constructor(context: Context) {

    companion object {
        private var instance: CoinRepository? = null

        fun getInstance(context: Context): CoinRepository {
            if (instance == null) {
                instance = CoinRepository(context)
            }
            return instance!!
        }
    }

    private val coinDao = CoinDatabase.getInstance(context).coinDao()

    @WorkerThread
    suspend fun insertCoin(coin: Coin) {
        coinDao.insertAll(coin)
    }

    @WorkerThread
    suspend fun deleteCoin(coin: Coin) {
        coinDao.delete(coin)
    }

    @WorkerThread
    suspend fun getAllFromDatabase(): List<Coin> {
        return try {
            coinDao.getAll()
        } catch (e: Exception) {
            emptyList()
        }
    }

    @WorkerThread
    suspend fun loadCoins(): List<Coin> {
        var coins: List<Coin> = coinDao.getAll()
        if (coins.isEmpty()) return coins // Needs to return when coins are empty, otherwise api would load all coins

        try {
            coins = ApiService.getInstance().getCoins(coins.map { coin -> coin.id })
            coinDao.insertAll(*coins.toTypedArray())
        } catch (e: Exception) {

        }

        return coins
    }
}