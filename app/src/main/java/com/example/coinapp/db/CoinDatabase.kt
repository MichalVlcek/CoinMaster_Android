package com.example.coinapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coinapp.data.Coin

@Database(entities = [Coin::class], version = 1)
abstract class CoinDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao

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