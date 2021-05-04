package com.example.coinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coinapp.ui.addCoin.AddCoinFragment
import com.example.coinapp.ui.homeScreen.HomeScreenFragment

class AddCoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, AddCoinFragment.newInstance())
                .commitNow()
        }
    }
}