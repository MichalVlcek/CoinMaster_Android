package com.example.coinapp.data.repositories

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.coinapp.db.CoinDatabase
import com.example.coinapp.model.Transaction
import com.example.coinapp.utils.UserUtils

class TransactionRepository(context: Context) {
    companion object {
        private var instance: TransactionRepository? = null
        private var loggedUserId: Long = 0

        fun getInstance(context: Context): TransactionRepository {
            loggedUserId = UserUtils.getLoggedUserId(context)
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
    suspend fun getAllTransactionsForUser(): List<Transaction> {
        return transactionDao.getAllForUser(loggedUserId)
    }

    @WorkerThread
    suspend fun getTransactionsByCoinId(coinId: String): List<Transaction> {
        return try {
            transactionDao.loadAllByCoinForUser(coinId, loggedUserId)
        } catch (e: Exception) {
            emptyList()
        }
    }
}