package com.example.coinapp.ui.homeScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ViewSwitcher
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.coinapp.R
import com.example.coinapp.data.Coin
import com.example.coinapp.databinding.HomeWatchedCoinItemBinding

class HomeScreenAdapter(private val switcher: ViewSwitcher, private val onClick: (Coin) -> Unit) :
    RecyclerView.Adapter<HomeScreenAdapter.ViewHolder>() {

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

    class ViewHolder(itemBinding: HomeWatchedCoinItemBinding, val onClick: (Coin) -> Unit) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val icon = itemBinding.coinIcon
        private val name = itemBinding.coinName
        private val price = itemBinding.coinPrice

        fun bind(coin: Coin) {
            icon.load(coin.icon)
            name.text = coin.name
            price.text = "${coin.price}$"

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
        holder.bind(coins[position])
    }

    override fun getItemCount(): Int = coins.count()
}