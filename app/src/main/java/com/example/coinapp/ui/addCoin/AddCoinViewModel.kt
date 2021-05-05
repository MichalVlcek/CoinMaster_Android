package com.example.coinapp.ui.addCoin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapp.data.Coin
import drewcarlson.coingecko.CoinGeckoClient
import kotlinx.coroutines.launch
import java.util.*

class AddCoinViewModel : ViewModel() {
    private val _items = MutableLiveData<List<Coin>>().apply {
        value = emptyList()
    }

    val items: LiveData<List<Coin>>
        get() = _items

    fun fetchData() {
        val coinGecko = CoinGeckoClient.create()

        viewModelScope.launch {
            val temp = coinGecko.getCoinMarkets(
                vsCurrency = "usd",
                perPage = 250,
                page = 1
            ).markets.map { market ->
                Coin(
                    icon = market.image ?: "",
                    rank = market.marketCapRank.toInt(),
                    name = market.symbol?.toUpperCase(Locale.getDefault()) ?: "",
                    price = market.currentPrice,
                    marketCap = market.marketCap
                )
            }

            _items.postValue(temp)
        }
    }
}