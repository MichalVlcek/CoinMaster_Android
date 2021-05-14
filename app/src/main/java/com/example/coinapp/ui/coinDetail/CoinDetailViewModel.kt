package com.example.coinapp.ui.coinDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coinapp.data.Coin

class CoinDetailViewModel : ViewModel() {

    private val _coin = MutableLiveData<Coin>()

    val coin: LiveData<Coin>
        get() = _coin

    fun setCoin(newCoin: Coin) {
        _coin.value = newCoin
    }
}