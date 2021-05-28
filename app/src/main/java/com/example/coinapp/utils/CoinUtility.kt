package com.example.coinapp.utils

import com.example.coinapp.model.Coin
import com.example.coinapp.model.Transaction
import com.example.coinapp.model.enums.FeeType
import com.example.coinapp.model.enums.TransactionType
import java.time.LocalDate

object CoinUtility {
    /**
     * Counts total amount of held coin
     * Formula: Sum of all buy transactions - Sum of Sell transactions - Sum of Send_Out transactions - Coin Fees
     */
    fun countHoldings(transactions: List<Transaction>): Double {
        val buyTransactions =
            getSumOfTransactionsAmount(getTransactionsByType(transactions, TransactionType.BUY))

        val sellTransactions =
            getSumOfTransactionsAmount(getTransactionsByType(transactions, TransactionType.SELL))

        val sendInTransactions =
            getSumOfTransactionsAmount(getTransactionsByType(transactions, TransactionType.RECEIVE))

        val sendOutTransactions =
            getSumOfTransactionsAmount(getTransactionsByType(transactions, TransactionType.SEND))

        return buyTransactions + sendInTransactions - sellTransactions - sendOutTransactions -
                getFeesSum(transactions, FeeType.COIN)
    }
//
    /**
     * Counts current value of held coins
     * Formula: Sum of all holdings * current price of coin
     */
    fun countHoldingsValue(transactions: List<Transaction>, coinPrice: Double): Double {
        return countHoldings(transactions) * coinPrice
    }

    fun countHoldingsValue(transactions: List<Transaction>, coins: List<Coin>): Double {
        return coins.map { coin ->
            countHoldingsValue(
                transactions.filter { t -> t.coinId == coin.id },
                coin.price
            )
        }.sum()
    }

    fun countHoldingsValueHistorical(
        transactions: List<Transaction>,
        coinPrices: Map<String, Double>,
        date: LocalDate
    ): Double {
        return coinPrices.map { (id, price) ->
            countHoldingsValue(
                transactions.filter { t -> t.coinId == id && t.date <= date },
                price
            )
        }.sum()
    }

    /**
     * Counts total amount of money spent on the currently held coins
     * Formula: Sum of cost of all Buy Transactions - Sum of all Sell transactions + Sum of All Fees
     */
    fun countTotalCost(transactions: List<Transaction>): Double {
        val buyTransactions =
            getSumOfTransactionCost(getTransactionsByType(transactions, TransactionType.BUY))
        val sellTransactions =
            getSumOfTransactionCost(getTransactionsByType(transactions, TransactionType.SELL))

        return buyTransactions - sellTransactions + getFeesSum(
            transactions,
            FeeType.DOLLAR
        ) + getCoinFeesInDollars(transactions)
    }

    /**
     * Counts average price of all transactions
     * Formula: Sum of transactions cost / Sum of current holdings
     */
    fun countAverageTransactionCost(transactions: List<Transaction>): Double {
        val holdings = countHoldings(transactions)

        if (holdings == 0.0) {
            return 0.0
        }
        return countTotalCost(transactions) / holdings
    }

    /**
     * Counts difference between current value of coins and total cost of coins
     * Formula: Current holdings value - Total cost
     */
    fun countProfitOrLoss(transactions: List<Transaction>, coin: Coin): Double {
        return countHoldingsValue(transactions, coin.price) - countTotalCost(transactions)
    }

    /**
     * Counts the percentage difference between value of current holdings and total cost
     * Formula: Current holdings value / Total Cost - 1
     */
    fun countPercentageChange(transactions: List<Transaction>, coin: Coin): Double {
        val totalCost = countTotalCost(transactions)
        if (totalCost == 0.0) {
            return 0.0
        }
        return (countHoldingsValue(transactions, coin.price) / totalCost) - 1
    }

    /**
     * Returns List of transactions with transaction type of [type]
     */
    private fun getTransactionsByType(
        transactions: List<Transaction>,
        type: TransactionType
    ): List<Transaction> {
        return transactions
            .filter { transaction -> transaction.type == type }
    }

    /**
     * Returns sum of all [FeeType.DOLLAR] fees
     */
    private fun getFeesSum(transactions: List<Transaction>, type: FeeType): Double {
        return transactions
            .filter { transaction -> transaction.feeType == type }
            .map { transaction -> transaction.fee }
            .sum()
    }

    /**
     * Returns dollar value of all transactions with [FeeType.COIN]
     */
    private fun getCoinFeesInDollars(transactions: List<Transaction>): Double {
        return transactions
            .filter { transaction -> transaction.feeType == FeeType.COIN }
            .map { transaction -> transaction.fee * (transaction.cost / transaction.amount) }
            .sum()
    }

    /**
     * Return sum of coin amount of all transactions from [pickedTransactions] list
     */
    private fun getSumOfTransactionsAmount(pickedTransactions: List<Transaction>): Double {
        return pickedTransactions
            .map { transaction -> transaction.amount }
            .sum()
    }

    /**
     * Returns sum of cost of all transactions from [pickedTransactions] List
     */
    private fun getSumOfTransactionCost(pickedTransactions: List<Transaction>): Double {
        return pickedTransactions
            .map { transaction -> transaction.cost }
            .sum()
    }
}