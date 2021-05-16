package com.example.coinapp.ui.addCoin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapp.api.ApiService
import com.example.coinapp.data.Coin
import com.example.coinapp.data.CoinList
import kotlinx.coroutines.async

class AddCoinViewModel : ViewModel() {

    private val _items = MutableLiveData<List<Coin>>().apply {
        value = emptyList()
    }

    val items: LiveData<List<Coin>>
        get() = _items

    /**
     * Clears [_items]
     */
    fun clearItems() {
        _items.value = emptyList()
    }

    /**
     * Adds [coin] to database
     */
    fun addCoin(coin: Coin) {
        CoinList.coins.add(coin)
    }

    /**
     * Calls ApiService for values and updates [_items]
     * Request is awaited in case of exceptions
     */
    suspend fun fetchData() {
        val request = viewModelScope.async() {
            _items.postValue(ApiService.getInstance().getCoins())
        }
        request.await()
    }
}