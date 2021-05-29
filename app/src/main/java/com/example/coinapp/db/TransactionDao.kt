package com.example.coinapp.db

import androidx.room.*
import com.example.coinapp.model.Transaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction` WHERE user_id=:userId ORDER BY date(date) DESC")
    suspend fun getAllForUser(userId: Long): List<Transaction>

    @Query("SELECT * FROM `transaction` WHERE coin_id = :coinId AND user_id = :userId ORDER BY date(date) DESC")
    suspend fun loadAllByCoinForUser(coinId: String, userId: Long): List<Transaction>

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