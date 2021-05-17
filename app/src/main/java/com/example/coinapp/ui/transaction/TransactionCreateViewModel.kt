package com.example.coinapp.ui.transaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapp.data.Transaction
import com.example.coinapp.data.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionCreateViewModel(application: Application) : AndroidViewModel(application) {
    private val transactionRepository by lazy { TransactionRepository.getInstance(application) }

    /**
     * Creates a new transaction
     */
    fun createNewTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                transactionRepository.insertTransaction(transaction)
            } catch (e: Exception) {

            }
        }
    }
}