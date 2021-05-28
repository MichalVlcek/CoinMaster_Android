package com.example.coinapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        const val LOGIN = "login"
        const val USER_ID = "user_id"
    }

    private val idDefault: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences(LOGIN, MODE_PRIVATE)

        val loggedUserId = sharedPreferences.getLong(USER_ID, idDefault)

        if (loggedUserId == 0.toLong()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
        }
    }
}