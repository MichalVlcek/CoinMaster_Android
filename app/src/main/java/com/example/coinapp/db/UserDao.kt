package com.example.coinapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coinapp.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun loadByEmail(email: String): User?

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun loadById(id: Int): User

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(vararg users: User)
}