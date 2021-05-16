package com.example.coinapp.ui.addCoin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coinapp.api.ApiService
import com.example.coinapp.data.Coin
import com.example.coinapp.data.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class AddCoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository by lazy { CoinRepository(application) }

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
    suspend fun addCoin(coin: Coin) {
        val request = viewModelScope.async(Dispatchers.IO) {
            repository.insertCoin(coin)
        }
        request.await()
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