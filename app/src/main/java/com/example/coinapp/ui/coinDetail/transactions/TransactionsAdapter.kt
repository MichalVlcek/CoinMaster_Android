package com.example.coinapp.ui.coinDetail.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ViewSwitcher
import androidx.recyclerview.widget.RecyclerView
import com.example.coinapp.R
import com.example.coinapp.data.Coin
import com.example.coinapp.data.FeeType
import com.example.coinapp.data.Transaction
import com.example.coinapp.databinding.TransactionsItemBinding
import com.example.coinapp.helper.StringOperations

class TransactionsAdapter(
    private val switcher: ViewSwitcher,
    private val coin: Coin?,
    private val onClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {

    var transactions = listOf<Transaction>()
        set(value) {
            field = value
            notifyDataSetChanged()
            if (value.isEmpty()) {
                if (switcher.currentView.id != R.id.loadingBar) {
                    switcher.showNext()
                }
            } else if (switcher.currentView.id != R.id.transactionsList) {
                switcher.showNext()
            }
        }

    class ViewHolder(
        itemBinding: TransactionsItemBinding,
        val coin: Coin?,
        val onClick: (Transaction) -> Unit
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val type = itemBinding.type
        private val date = itemBinding.date
        private val cost = itemBinding.cost
        private val amount = itemBinding.amount
        private val fee = itemBinding.fee

        fun bind(transaction: Transaction) {
            type.text = transaction.type.toString()
            date.text = transaction.date.toString()
            cost.text = StringOperations.formatCurrency(transaction.cost)
            amount.text = coin?.let { StringOperations.formatCurrency(transaction.amount, it) }

            fee.text = when (transaction.feeType) {
                FeeType.DOLLAR -> StringOperations.formatCurrency(transaction.fee)
                FeeType.COIN -> coin?.let { StringOperations.formatCurrency(transaction.fee, it) }
            }

            itemView.setOnClickListener { onClick(transaction) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            TransactionsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            coin,
            onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int = transactions.size
}