package com.example.coinapp.ui.coinDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coinapp.data.Coin
import com.example.coinapp.data.Transaction
import com.example.coinapp.data.TransactionType
import java.time.LocalDate

class CoinDetailViewModel : ViewModel() {

    private val _coin = MutableLiveData<Coin>()

    private val _transactions = MutableLiveData<List<Transaction>>().apply {
        value = listOf(
            Transaction(TransactionType.BUY, LocalDate.now(), 20.0, 0.0005),
            Transaction(TransactionType.BUY, LocalDate.now(), 20.0, 0.0005),
            Transaction(TransactionType.BUY, LocalDate.now(), 20.0, 0.0005),
            Transaction(TransactionType.BUY, LocalDate.now(), 20.0, 0.0005),
            Transaction(TransactionType.BUY, LocalDate.now(), 20.0, 0.0005),
        )
    }

    val transactions: LiveData<List<Transaction>>
        get() = _transactions

    val coin: LiveData<Coin>
        get() = _coin

    fun setCoin(newCoin: Coin) {
        _coin.value = newCoin
    }
}