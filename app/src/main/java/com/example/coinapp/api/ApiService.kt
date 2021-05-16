package com.example.coinapp.api

import com.example.coinapp.data.Coin
import drewcarlson.coingecko.CoinGeckoClient
import java.util.*

class ApiService {
    private val coinGecko = CoinGeckoClient.create()

    companion object {
        private var instance: ApiService? = null

        fun getInstance(): ApiService {
            if (instance == null) {
                instance = ApiService()
            }
            return instance!!
        }
    }

    /**
     * Gets a list of coins from external api
     */
    suspend fun getCoins(): List<Coin> {
        return coinGecko.getCoinMarkets(
            vsCurrency = "usd",
            perPage = 250,
            page = 1
        ).markets.map { market ->
            Coin(
                id = market.id ?: "",
                icon = market.image ?: "",
                rank = market.marketCapRank.toInt(),
                name = market.name ?: "",
                symbol = market.symbol?.uppercase(Locale.getDefault()) ?: "",
                price = market.currentPrice,
                marketCap = market.marketCap,
                supply = market.circulatingSupply
            )
        }
    }
}