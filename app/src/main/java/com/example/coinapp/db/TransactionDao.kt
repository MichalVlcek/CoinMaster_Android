package com.example.coinapp.db

import androidx.room.*
import com.example.coinapp.data.Transaction

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction`  ORDER BY date(date) DESC")
    fun getAll(): List<Transaction>

    @Query("SELECT * FROM `transaction` WHERE coin_id = :coinId")
    fun loadAllByCoin(coinId: String): List<Transaction>

    @Query("SELECT * FROM `transaction` WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Transaction>

    @Query("SELECT * FROM `transaction` WHERE id = :id")
    fun loadById(id: Int): Transaction

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg transactions: Transaction)

    @Delete
    fun delete(transaction: Transaction)
}