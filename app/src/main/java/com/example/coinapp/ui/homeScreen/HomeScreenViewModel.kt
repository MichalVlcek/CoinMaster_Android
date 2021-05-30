package com.example.coinapp.ui.homeScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coinapp.api.ApiService
import com.example.coinapp.data.repositories.CoinRepository
import com.example.coinapp.data.repositories.TransactionRepository
import com.example.coinapp.model.Coin
import com.example.coinapp.model.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class HomeScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val coinRepository by lazy { CoinRepository.getInstance(application) }
    private val transactionRepository by lazy { TransactionRepository.getInstance(application) }

    private val _coins = MutableLiveData<List<Coin>>().apply { value = emptyList() }
    private val _transactions = MutableLiveData<List<Transaction>>().apply { value = emptyList() }

    val coins: LiveData<List<Coin>>
        get() = _coins

    val transactions: LiveData<List<Transaction>>
        get() = _transactions

    fun getTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            _transactions.postValue(transactionRepository.getAllTransactionsForUser())
        }
    }

    fun getCoinsFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            _coins.postValue(coinRepository.getAllFromDatabase())
        }
    }

    fun updateCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            _coins.postValue(coinRepository.loadCoins())
        }
    }

    suspend fun getHistoricalPrices(date: LocalDate): Map<String, Double> {
        return withContext(Dispatchers.IO) {
            coins.value?.associateBy(
                { it.id },
                { ApiService.getInstance().getHistoricalCoinPrice(it.id, date) })
                ?: emptyMap()
        }
    }

    fun clearCoinList() {
        _coins.value = emptyList()
    }
}