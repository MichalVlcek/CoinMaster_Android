package com.example.coinapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coinapp.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM coin WHERE id = :id")
    fun loadById(id: Int): User

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(vararg users: User)
}