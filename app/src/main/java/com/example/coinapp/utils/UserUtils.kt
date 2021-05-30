package com.example.coinapp.utils

import android.content.Context
import java.security.MessageDigest

object UserUtils {
    private const val algorithm = "SHA-256"

    private const val LOGIN = "login"
    private const val USER_ID = "user_id"
    private const val DEFAULT_ID: Long = 0

    fun hashPassword(password: String): String {
        return MessageDigest.getInstance(algorithm)
            .digest(password.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }

    fun getLoggedUserId(context: Context): Long {
        return context
            .getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
            .getLong(USER_ID, DEFAULT_ID)
    }

    fun storeLoggedUserId(userId: Long, context: Context) {
        context
            .getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
            .edit()
            .putLong(USER_ID, userId)
            .apply()
    }

    fun logoutUser(context: Context) {
        storeLoggedUserId(0, context)
    }
}