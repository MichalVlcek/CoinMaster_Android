package com.example.coinapp.ui.addCoin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapp.api.ApiService
import com.example.coinapp.data.Coin
import kotlinx.coroutines.async

class AddCoinViewModel : ViewModel() {

    private val _items = MutableLiveData<List<Coin>>().apply {
        value = emptyList()
    }

    val items: LiveData<List<Coin>>
        get() = _items

    suspend fun fetchData() {
        val request = viewModelScope.async() {
            _items.postValue(ApiService.getInstance().getCoins())
        }
        request.await()
    }

    fun clearItems() {
        _items.value = emptyList()
    }
}