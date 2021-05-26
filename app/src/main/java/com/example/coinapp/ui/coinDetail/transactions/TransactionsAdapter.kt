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
import com.example.coinapp.utils.StringOperations
import com.example.coinapp.utils.TextViewOperations.setTextAndColor

class TransactionsAdapter(
    private val switcher: ViewSwitcher,
    private val emptySwitcher: ViewSwitcher,
    private val coin: Coin?,
    private val onClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {

    private var firstRefresh = true // used only as workaround for view switching

    var transactions = listOf<Transaction>()
        set(value) {
            handleViewSwitching(transactions, value)

            firstRefresh = false
            field = value
            notifyDataSetChanged()
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
            type.setTextAndColor(transaction.type)
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

    private fun handleViewSwitching(oldValue: List<Transaction>, newValue: List<Transaction>) {
        if (newValue.isEmpty()) {
            if (switcher.currentView.id != R.id.empty_switcher) {
                switcher.showNext()
            }

            if (
                (emptySwitcher.currentView.id != R.id.no_transactions
                        && !firstRefresh && oldValue.isEmpty())
                ||
                (emptySwitcher.currentView.id != R.id.loadingBar
                        && (firstRefresh || oldValue.isNotEmpty()))
            ) {
                emptySwitcher.showNext()
            }

        } else if (switcher.currentView.id != R.id.transactionsList) {
            switcher.showNext()
        }
    }
}