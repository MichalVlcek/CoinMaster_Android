package com.example.coinapp.ui.homeScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coinapp.data.Coin
import com.example.coinapp.data.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val repository by lazy { CoinRepository.getInstance(application) }

    private val _items = MutableLiveData<List<Coin>>().apply { value = emptyList() }

    val items: LiveData<List<Coin>>
        get() = _items

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            _items.postValue(repository.getAllFromDatabase())
        }
    }

    fun updateData() {
        viewModelScope.launch(Dispatchers.IO) {
            _items.postValue(repository.loadCoins())
        }
    }

    fun clearData() {
        _items.value = emptyList()
    }
}