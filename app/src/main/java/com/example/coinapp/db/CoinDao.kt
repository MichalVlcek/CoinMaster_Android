package com.example.coinapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.coinapp.data.Coin

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin")
    fun getAll(): List<Coin>

    @Query("SELECT * FROM coin WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): LiveData<List<Coin>>

    @Query("SELECT * FROM coin WHERE id = :id")
    fun loadById(id: Int): LiveData<Coin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg coins: Coin)

    @Delete
    fun delete(coin: Coin)
}