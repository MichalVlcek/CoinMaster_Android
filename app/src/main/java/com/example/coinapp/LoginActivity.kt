package com.example.coinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coinapp.ui.login.LoginFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, LoginFragment.newInstance())
                .commitNow()
        }
    }
}