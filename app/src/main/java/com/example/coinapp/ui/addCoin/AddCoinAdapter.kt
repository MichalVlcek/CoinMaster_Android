package com.example.coinapp.ui.addCoin

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ViewSwitcher
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.coinapp.R
import com.example.coinapp.data.Coin
import com.example.coinapp.databinding.AddCoinItemBinding

class AddCoinAdapter(private val switcher: ViewSwitcher, private val onClick: (Coin) -> Unit) :
    RecyclerView.Adapter<AddCoinAdapter.ViewHolder>() {

    var coins = listOf<Coin>()
        set(value) {
            field = value
            notifyDataSetChanged()
            if (value.isEmpty()) {
                if (switcher.currentView.id != R.id.loadingBar) {
                    switcher.showNext()
                }
            } else if (switcher.currentView.id != R.id.watchedCoinsList) {
                switcher.showNext()
            }
        }

    class ViewHolder(itemBinding: AddCoinItemBinding, val onClick: (Coin) -> Unit) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val icon = itemBinding.coinIcon
        private val rank = itemBinding.coinRank
        private val name = itemBinding.coinName
        private val price = itemBinding.coinPrice
        private val marketCap = itemBinding.coinCap

        //TODO Change the string implementation
        fun bind(coin: Coin) {
            icon.load(coin.icon)
            rank.text = "#${coin.rank}"
            name.text = coin.name
            price.text = "${coin.price}$"
            marketCap.text = "${coin.marketCap.toBigDecimal().toPlainString()}$"

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
        holder.bind(coins[position])
    }

    override fun getItemCount(): Int = coins.count()

}