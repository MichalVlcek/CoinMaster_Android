package com.example.coinapp.data

import android.content.Context
import com.example.coinapp.db.CoinDatabase
import com.example.coinapp.ui.register.UserExistsException

class UserRepository(context: Context) {
    companion object {
        private var instance: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            if (instance == null) {
                instance = UserRepository(context)
            }
            return instance!!
        }
    }

    private val userDao = CoinDatabase.getInstance(context).userDao()

    suspend fun registerUser(user: User) {
        if (userDao.loadByEmail(user.email) != null) {
            throw UserExistsException("This user already exists")
        }
        userDao.insertAll(user)
    }
}