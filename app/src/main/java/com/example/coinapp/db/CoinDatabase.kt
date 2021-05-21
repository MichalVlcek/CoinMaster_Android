package com.example.coinapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.coinapp.data.Coin
import com.example.coinapp.data.DataTypeConverters
import com.example.coinapp.data.Transaction
import com.example.coinapp.data.User

@Database(entities = [Coin::class, Transaction::class, User::class], version = 1)
@TypeConverters(DataTypeConverters::class)
abstract class CoinDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao

    abstract fun transactionDao(): TransactionDao

    abstract fun userDao(): UserDao

    companion object {
        private var instance: CoinDatabase? = null

        fun getInstance(context: Context): CoinDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(
                        context,
                        CoinDatabase::class.java,
                        "tracker"
                    ).build()
            }
            return instance!!
        }
    }
}