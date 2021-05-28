package com.example.coinapp.ui.transaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapp.data.Transaction
import com.example.coinapp.data.repositories.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionManageViewModel(application: Application) : AndroidViewModel(application) {
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

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                transactionRepository.updateTransaction(transaction)
            } catch (e: Exception) {

            }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                transactionRepository.deleteTransaction(transaction)
            } catch (e: Exception) {

            }
        }
    }
}