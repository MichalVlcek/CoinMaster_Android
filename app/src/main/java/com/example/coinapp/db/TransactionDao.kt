package com.example.coinapp.db

import androidx.room.*
import com.example.coinapp.data.Transaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction`  ORDER BY date(date) DESC")
    suspend fun getAll(): List<Transaction>

    @Query("SELECT * FROM `transaction` WHERE coin_id = :coinId ORDER BY date(date) DESC")
    suspend fun loadAllByCoin(coinId: String): List<Transaction>

    @Query("SELECT * FROM `transaction` WHERE id IN (:ids)")
    suspend fun loadAllByIds(ids: IntArray): List<Transaction>

    @Query("SELECT * FROM `transaction` WHERE id = :id")
    suspend fun loadById(id: Int): Transaction

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg transactions: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)
}