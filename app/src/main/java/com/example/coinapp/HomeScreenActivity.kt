package com.example.coinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coinapp.homeScreen.HomeScreenFragment

class HomeScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, HomeScreenFragment.newInstance())
                .commitNow()
        }
    }
}