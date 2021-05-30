package com.example.coinapp.data.repositories

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.coinapp.api.ApiService
import com.example.coinapp.data.ConfigValues
import com.example.coinapp.db.CoinDatabase
import com.example.coinapp.exceptions.MaximumCoinsException
import com.example.coinapp.model.Coin
import com.example.coinapp.model.UserCoinDataJoin
import com.example.coinapp.utils.UserUtils

class CoinRepository private constructor(context: Context) {

    companion object {
        private var instance: CoinRepository? = null
        private var loggedUserId: Long = 0

        fun getInstance(context: Context): CoinRepository {
            loggedUserId = UserUtils.getLoggedUserId(context)
            if (instance == null) {
                instance = CoinRepository(context)
            }
            return instance!!
        }
    }

    private val coinDao = CoinDatabase.getInstance(context).coinDao()
    private val userDao = CoinDatabase.getInstance(context).userDao()
    private val userCoinJoinDao = CoinDatabase.getInstance(context).userCoinJoinDao()

    @WorkerThread
    suspend fun insertCoin(coin: Coin) {
        val user = userDao.loadById(loggedUserId)
        if (!user.premium && countCoinsForUser() >= ConfigValues.BASiC_USER_MAX_COINS) {
            throw MaximumCoinsException("Maximum threshold for coins would be exceeded")
        }

        coinDao.insertAll(coin)
        userCoinJoinDao.insert(UserCoinDataJoin(loggedUserId, coin.id))
    }

    @WorkerThread
    suspend fun deleteCoin(coin: Coin) {
        userCoinJoinDao.deleteCoinForUser(loggedUserId, coin.id)
    }

    @WorkerThread
    suspend fun getAllFromDatabase(): List<Coin> {
        return try {
            userCoinJoinDao.getCoinsForUser(loggedUserId)
        } catch (e: Exception) {
            emptyList()
        }
    }

    @WorkerThread
    suspend fun loadCoins(): List<Coin> {
        var coins: List<Coin> = userCoinJoinDao.getCoinsForUser(loggedUserId)
        if (coins.isEmpty()) return coins // Needs to return when coins are empty, otherwise api would load all coins

        try {
            coins = ApiService.getInstance().getCoins(coins.map { coin -> coin.id })
            coinDao.updateAll(*coins.toTypedArray())
        } catch (e: Exception) {

        }

        return coins
    }

    @WorkerThread
    suspend fun countCoinsForUser(): Int {
        return userCoinJoinDao.countCoinsForUser(loggedUserId)
    }
}