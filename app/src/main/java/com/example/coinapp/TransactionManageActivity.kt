package com.example.coinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coinapp.model.Coin
import com.example.coinapp.model.Transaction
import com.example.coinapp.ui.transaction.TransactionCreateFragment
import com.example.coinapp.ui.transaction.TransactionEditFragment

class TransactionManageActivity : AppCompatActivity() {
    companion object {
        const val COIN = "coin"
        const val TRANSACTION = "transaction"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val coin = intent.extras?.getParcelable<Coin>(COIN)
        val transaction = intent.extras?.getParcelable<Transaction>(TRANSACTION)

        supportActionBar?.title = coin?.name

        if (savedInstanceState == null) {
            if (transaction == null) {
                supportFragmentManager.beginTransaction()
                    .replace(
                        android.R.id.content,
                        TransactionCreateFragment.newInstance(coin)
                    )
                    .commitNow()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(
                        android.R.id.content,
                        TransactionEditFragment.newInstance(coin, transaction)
                    )
                    .commitNow()
            }
        }
    }
}