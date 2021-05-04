package com.example.coinapp.ui.addCoin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapp.data.Coin
import drewcarlson.coingecko.CoinGeckoClient
import kotlinx.coroutines.launch

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
            ).markets.map { market -> Coin(market.name ?: "", market.currentPrice) }

            _items.postValue(temp)
        }
    }
}