package com.example.coinapp.ui.coinDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coinapp.data.Coin
import com.example.coinapp.data.Transaction
import com.example.coinapp.data.repositories.CoinRepository
import com.example.coinapp.data.repositories.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val coinRepository by lazy { CoinRepository.getInstance(application) }
    private val transactionRepository by lazy { TransactionRepository.getInstance(application) }

    private val _coin = MutableLiveData<Coin>()

    private val _transactions = MutableLiveData<List<Transaction>>().apply {
        value = emptyList()
    }

    val transactions: LiveData<List<Transaction>>
        get() = _transactions

    val coin: LiveData<Coin>
        get() = _coin

    fun unwatchCoin() {
        viewModelScope.launch(Dispatchers.IO) {
            coin.value?.let { coinRepository.deleteCoin(it) }
        }
    }

    /**
     * Sets new value to [_coin] LiveData field
     */
    fun setCoin(newCoin: Coin) {
        _coin.value = newCoin
    }

    fun clearTransactions() {
        _transactions.value = emptyList()
    }

    /**
     * Updates [_transactions] LiveData field with sorted transactions from database
     */
    fun getTransactionsByCoinId(coinId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _transactions.postValue(transactionRepository.getTransactionsByCoinId(coinId!!))
            } catch (e: Exception) {

            }
        }
    }
}