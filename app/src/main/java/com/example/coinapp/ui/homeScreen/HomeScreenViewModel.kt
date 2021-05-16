package com.example.coinapp.ui.homeScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coinapp.data.Coin
import com.example.coinapp.data.CoinList
import com.example.coinapp.data.CoinRepository

class HomeScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepository(application)

    private val _items = MutableLiveData<List<Coin>>().apply { value = emptyList() }

    val items: LiveData<List<Coin>>
        get() = _items

    /**
     * ONLY TEMPORARY UNTIL DATABASE IS USED
     */
    fun updateData() {
        _items.value = CoinList.coins
    }
}