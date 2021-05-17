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
    suspend fun deleteCoin(transaction: Transaction) {
        transactionDao.delete(transaction)
    }

    @WorkerThread
    suspend fun getAllFromDatabase(): List<Transaction> {
        return try {
            transactionDao.getAll()
        } catch (e: Exception) {
            emptyList()
        }
    }
}