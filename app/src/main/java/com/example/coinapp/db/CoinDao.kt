package com.example.coinapp.db

import androidx.room.*
import com.example.coinapp.data.Coin

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin")
    suspend fun getAll(): List<Coin>

    @Query("SELECT * FROM coin WHERE id IN (:ids)")
    suspend fun loadAllByIds(ids: IntArray): List<Coin>

    @Query("SELECT * FROM coin WHERE id = :id")
    suspend fun loadById(id: Int): Coin

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg coins: Coin)

    @Delete
    suspend fun delete(coin: Coin)
}