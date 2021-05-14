package com.example.coinapp.ui.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coinapp.data.Coin
import com.example.coinapp.data.CoinList

class HomeScreenViewModel : ViewModel() {
    private val _items = MutableLiveData<List<Coin>>().apply { value = emptyList()}

    val items: LiveData<List<Coin>>
        get() = _items

    /**
     * ONLY TEMPORARY UNTIL DATABASE IS USED
     */
    fun updateData() {
        _items.value = CoinList.coins
    }
}