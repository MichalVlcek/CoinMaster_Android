package com.example.coinapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coinapp.utils.UserUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loggedUserId = UserUtils.getLoggedUserId(this)

        if (loggedUserId == 0.toLong()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
        }
    }
}