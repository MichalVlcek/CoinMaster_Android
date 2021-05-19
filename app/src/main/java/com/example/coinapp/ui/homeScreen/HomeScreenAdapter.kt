package com.example.coinapp.ui.homeScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ViewSwitcher
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.coinapp.R
import com.example.coinapp.data.Coin
import com.example.coinapp.data.Transaction
import com.example.coinapp.databinding.HomeWatchedCoinItemBinding
import com.example.coinapp.helper.CoinUtility
import com.example.coinapp.helper.StringOperations

class HomeScreenAdapter(
    private val switcher: ViewSwitcher,
    private val emptySwitcher: ViewSwitcher,
    private val onClick: (Coin) -> Unit
) :
    RecyclerView.Adapter<HomeScreenAdapter.ViewHolder>() {

    private var firstRefresh = true // used only for switching of views in emptySwitcher

    var coins = listOf<Coin>()
        set(value) {
            handleViewSwitching(coins, value)

            firstRefresh = false
            field = value.sortedByDescending { coin ->
                CoinUtility.countHoldingsValue(
                    transactions.filter { transaction -> transaction.coinId == coin.id },
                    coin
                )
            }
            notifyDataSetChanged()
        }

    var transactions = listOf<Transaction>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(itemBinding: HomeWatchedCoinItemBinding, val onClick: (Coin) -> Unit) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private val icon = itemBinding.coinIcon
        private val symbol = itemBinding.coinSymbol
        private val price = itemBinding.coinPrice
        private val holdings = itemBinding.dollarHoldings
        private val coinAmount = itemBinding.coinHoldings

        fun bind(coin: Coin, transactions: List<Transaction>) {
            val filteredTransactions =
                transactions.filter { transaction -> transaction.coinId == coin.id }

            icon.load(coin.icon)
            symbol.text = coin.symbol
            price.text = StringOperations.formatCurrency(coin.price)

            holdings.text = StringOperations.formatCurrency(
                CoinUtility.countHoldingsValue(
                    filteredTransactions,
                    coin
                )
            )

            coinAmount.text = StringOperations.formatCurrency(
                CoinUtility.countHoldings(filteredTransactions),
                coin
            )

            itemView.setOnClickListener { onClick(coin) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HomeWatchedCoinItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coins[position], transactions)
    }

    override fun getItemCount(): Int = coins.count()

    private fun handleViewSwitching(oldValue: List<Coin>, newValue: List<Coin>) {
        if (newValue.isEmpty()) {
            if (switcher.currentView.id != R.id.empty_switcher) {
                switcher.showNext()
            }

            if (emptySwitcher.currentView.id != R.id.no_transactions
                && !firstRefresh && oldValue.isEmpty()
            ) {
                emptySwitcher.showNext()

            }
            if (emptySwitcher.currentView.id != R.id.loadingBar
                && (firstRefresh || oldValue.isNotEmpty())
            ) {
                emptySwitcher.showNext()

            }

        } else if (switcher.currentView.id != R.id.watchedCoinsList) {
            switcher.showNext()
        }
    }
}