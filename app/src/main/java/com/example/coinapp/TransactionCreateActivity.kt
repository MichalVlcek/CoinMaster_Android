package com.example.coinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coinapp.data.Coin
import com.example.coinapp.ui.transaction.TransactionCreateFragment

class TransactionCreateActivity : AppCompatActivity() {
    companion object {
        const val COIN = "coin"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val coin = intent.extras?.getParcelable<Coin>(CoinDetailActivity.COIN)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    android.R.id.content,
                    TransactionCreateFragment.newInstance(coin)
                )
                .commitNow()
        }
    }
}