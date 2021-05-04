package com.example.coinapp.ui.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coinapp.data.Coin

class HomeScreenViewModel : ViewModel() {
    private val _items = MutableLiveData<List<Coin>>().apply { value = listOf(
        Coin("BTC", 2),
        Coin("BTC", 2),
        Coin("BTC", 2),
        Coin("BTC", 2),
        Coin("BTC", 2),
        Coin("BTC", 2),
        Coin("BTC", 2),
        Coin("BTC", 2),
        Coin("BTC", 2),
        Coin("BTC", 2),
        Coin("BTC", 2),
        Coin("BTC", 2),
        Coin("BTC", 2),
        Coin("BTC", 2),
        Coin("BTC", 2),
        Coin("BTC", 2),
    ) }

    val items: LiveData<List<Coin>>
        get() = _items

//    fun generateNewItems(count: Int) {
//        _items.value = ItemGenerator.generate(count)
//    }
}