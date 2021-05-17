package com.example.coinapp.api

import com.example.coinapp.data.Coin
import drewcarlson.coingecko.CoinGeckoClient

const val currency = "usd"
const val page = 1
const val itemsOnPage = 250

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
    suspend fun getCoins(coinIds: List<String> = emptyList()): List<Coin> {
        return coinGecko.getCoinMarkets(
            vsCurrency = currency,
            perPage = itemsOnPage,
            page = page,
            ids = coinIds.joinToString(",")
        ).markets.map { market ->
            Coin(
                id = market.id ?: "",
                icon = market.image ?: "",
                rank = market.marketCapRank.toInt(),
                name = market.name ?: "",
                symbol = market.symbol?.uppercase() ?: "",
                price = market.currentPrice,
                marketCap = market.marketCap,
                supply = market.circulatingSupply
            )
        }
    }
}