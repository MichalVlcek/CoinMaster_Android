package com.example.coinapp.utils

import java.security.MessageDigest

object UserUtils {
    private const val algorithm = "SHA-256"

    fun hashPassword(password: String): String {
        return MessageDigest.getInstance(algorithm)
            .digest(password.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }
}