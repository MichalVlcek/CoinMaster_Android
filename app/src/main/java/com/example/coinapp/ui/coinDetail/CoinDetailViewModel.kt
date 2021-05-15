package com.example.coinapp.ui.coinDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coinapp.data.Coin
import com.example.coinapp.data.FeeType
import com.example.coinapp.data.Transaction
import com.example.coinapp.data.TransactionType
import java.time.LocalDate

class CoinDetailViewModel : ViewModel() {

    private val _coin = MutableLiveData<Coin>()

    private val _transactions = MutableLiveData<List<Transaction>>().apply {
        value = listOf(
            Transaction(TransactionType.BUY, LocalDate.now(), 20.0, 0.0005, 0.1, FeeType.DOLLAR),
            Transaction(TransactionType.BUY, LocalDate.now(), 50.0, 0.001, 0.0001, FeeType.COIN),
            Transaction(TransactionType.SELL, LocalDate.now(), 20.0, 0.0008, 0.1, FeeType.DOLLAR),
            Transaction(TransactionType.BUY, LocalDate.now(), 5.0, 0.0005, 0.0001, FeeType.COIN),
            Transaction(TransactionType.BUY, LocalDate.now(), 20.0, 0.00012, 0.1, FeeType.DOLLAR),
            Transaction(TransactionType.SELL, LocalDate.now(), 15.0, 0.0001, 0.1, FeeType.DOLLAR),
            Transaction(TransactionType.BUY, LocalDate.now(), 20.0, 0.00022, 0.1, FeeType.DOLLAR),
        )
    }

    val transactions: LiveData<List<Transaction>>
        get() = _transactions

    val coin: LiveData<Coin>
        get() = _coin

    fun setCoin(newCoin: Coin) {
        _coin.value = newCoin
    }
    
    private fun getTransactionsByType(type: TransactionType): List<Transaction> {
        return transactions.value
            ?.filter { transaction -> transaction.type == type }
            ?: emptyList()
    }

    private fun getFeesSum(type: FeeType): Double {
        return transactions.value
            ?.filter { transaction -> transaction.feeType == type }
            ?.map { transaction -> transaction.fee }
            ?.sum() ?: 0.0
    }

    private fun getCoinFeesInDollars(): Double {
        return transactions.value
            ?.filter { transaction -> transaction.feeType == FeeType.COIN }
            ?.map { transaction -> transaction.fee * (transaction.cost / transaction.amount) }
            ?.sum() ?: 0.0
    }

    private fun getSumOfTransactionsAmount(pickedTransactions: List<Transaction>): Double {
        return pickedTransactions
            .map { transaction -> transaction.amount }
            .sum()
    }

    private fun getSumOfTransactionCost(pickedTransactions: List<Transaction>): Double {
        return pickedTransactions
            .map { transaction -> transaction.cost }
            .sum()
    }

    /**
     * Counts total amount of held coin
     * Formula: Sum of all buy transactions - Sum of Sell transactions - Sum of Send_Out transactions - Coin Fees
     */
    fun countHoldings(): Double {
        val buyTransactions = getSumOfTransactionsAmount(getTransactionsByType(TransactionType.BUY))

        val sellTransactions =
            getSumOfTransactionsAmount(getTransactionsByType(TransactionType.SELL))

        val sendInTransactions =
            getSumOfTransactionsAmount(getTransactionsByType(TransactionType.SEND_IN))

        val sendOutTransactions =
            getSumOfTransactionsAmount(getTransactionsByType(TransactionType.SEND_OUT))

        return buyTransactions + sendInTransactions - sellTransactions - sendOutTransactions -
                getFeesSum(FeeType.COIN)
    }

    /**
     * Counts current value of held coins
     * Formula: Sum of all holdings * current price of coin
     */
    fun countHoldingsValue(): Double {
        return countHoldings() * (coin.value?.price ?: 0.0)
    }

    /**
     * Counts total amount of money spent on the currently held coins
     * Formula: Sum of cost of all Buy Transactions - Sum of all Sell transactions + Sum of All Fees
     */
    fun countTotalCost(): Double {
        val buyTransactions = getSumOfTransactionCost(getTransactionsByType(TransactionType.BUY))
        val sellTransactions = getSumOfTransactionCost(getTransactionsByType(TransactionType.SELL))

        return buyTransactions - sellTransactions + getFeesSum(FeeType.DOLLAR) + getCoinFeesInDollars()
    }

    /**
     * Counts average price of all transactions
     * Formula: Sum of transactions cost / Sum of current holdings
     */
    fun countAverageTransactionCost(): Double {
        return countTotalCost() / countHoldings()
    }

    /**
     * Counts difference between current value of coins and total cost of coins
     * Formula: Current holdings value - Total cost
     */
    fun countProfitOrLoss(): Double {
        return countHoldingsValue() - countTotalCost()
    }

    /**
     * Counts the percentage difference between value of current holdings and total cost
     * Formula: Current holdings value / Total Cost
     */
    fun countPercentageChange(): Double {
        return countHoldingsValue() / countTotalCost()
    }
}