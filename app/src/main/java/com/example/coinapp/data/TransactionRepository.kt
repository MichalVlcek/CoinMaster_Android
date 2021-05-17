package com.example.coinapp.data

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.coinapp.db.CoinDatabase

class TransactionRepository(context: Context) {
    companion object {
        private var instance: TransactionRepository? = null

        fun getInstance(context: Context): TransactionRepository {
            if (instance == null) {
                instance = TransactionRepository(context)
            }
            return instance!!
        }
    }

    private val transactionDao = CoinDatabase.getInstance(context).transactionDao()

    @WorkerThread
    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertAll(transaction)
    }

    @WorkerThread
    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }

    @WorkerThread
    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.delete(transaction)
    }

    @WorkerThread
    suspend fun getTransactionsByCoinId(coinId: String): List<Transaction> {
        return try {
            transactionDao.loadAllByCoin(coinId)
        } catch (e: Exception) {
            emptyList()
        }
    }
}