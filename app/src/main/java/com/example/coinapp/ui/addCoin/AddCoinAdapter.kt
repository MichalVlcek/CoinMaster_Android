package com.example.coinapp.ui.addCoin

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ViewSwitcher
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.coinapp.R
import com.example.coinapp.databinding.AddCoinItemBinding
import com.example.coinapp.model.Coin
import com.example.coinapp.ui.FilterAdapter
import com.example.coinapp.utils.StringOperations

class AddCoinAdapter(
    private val switcher: ViewSwitcher,
    private val filterSwitcher: ViewSwitcher,
    private val onClick: (Coin) -> Unit
) : RecyclerView.Adapter<AddCoinAdapter.ViewHolder>(), FilterAdapter {

    var coins = listOf<Coin>()
        set(value) {
            field = value
            shownCoins = value
        }

    private var shownCoins = listOf<Coin>()
        set(value) {
            field = value
            notifyDataSetChanged()

            handleViewSwitching(coins, value)
        }

    class ViewHolder(itemBinding: AddCoinItemBinding, val onClick: (Coin) -> Unit) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val icon = itemBinding.coinIcon
        private val rank = itemBinding.coinRank
        private val symbol = itemBinding.coinSymbol
        private val price = itemBinding.coinPrice
        private val marketCap = itemBinding.coinCap

        /**
         * Binds values to TextViews
         */
        fun bind(coin: Coin) {
            icon.load(coin.icon)
            rank.text = StringOperations.formatRank(coin.rank)
            symbol.text = coin.symbol
            price.text = StringOperations.formatCurrency(coin.price)
            marketCap.text = StringOperations.formatCurrency(coin.marketCap)

            itemView.setOnClickListener { onClick(coin) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            AddCoinItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(shownCoins[position])
    }

    override fun getItemCount(): Int = shownCoins.count()

    override fun filterData(query: String) {
        shownCoins = coins.filter { coin ->
            coin.name.contains(query, true) ||
                    coin.symbol.contains(query, true) ||
                    coin.rank.toString() == query
        }
    }

    private fun handleViewSwitching(mainCoins: List<Coin>, filteredCoins: List<Coin>) {
        if (filteredCoins.isEmpty() && mainCoins.isEmpty()) {
            if (switcher.currentView.id != R.id.loadingBar) {
                switcher.showNext()
            }
        } else if (switcher.currentView.id != R.id.watchedCoinsList) {
            switcher.showNext()
        }

        if (filteredCoins.isEmpty() && mainCoins.isNotEmpty()) {
            if (filterSwitcher.currentView.id != R.id.filterFailed) {
                filterSwitcher.showNext()
            }

        } else if (filterSwitcher.currentView.id != R.id.switcher) {
            filterSwitcher.showNext()
        }
    }
}