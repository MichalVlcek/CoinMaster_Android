package com.example.coinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coinapp.data.Coin
import com.example.coinapp.ui.coinDetail.TabHolderFragment

class CoinDetailActivity : AppCompatActivity() {

    companion object {
        const val COIN = "coin"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_detail_tab_holder_fragment)

        if (savedInstanceState == null) {
            val coin = intent.extras?.getParcelable<Coin>(COIN)!!

            supportFragmentManager.beginTransaction()
                .replace(
                    android.R.id.content,
                    TabHolderFragment.newInstance(coin)
                )
                .commitNow()
        }
    }
}