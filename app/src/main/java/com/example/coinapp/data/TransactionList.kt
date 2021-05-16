package com.example.coinapp.data

import java.time.LocalDate

object TransactionList {
    var transactions: MutableList<Transaction> = mutableListOf(
        Transaction(TransactionType.BUY, LocalDate.now(), 20.0, 0.0005, 0.1, FeeType.DOLLAR),
        Transaction(TransactionType.BUY, LocalDate.now(), 50.0, 0.001, 0.0001, FeeType.COIN),
        Transaction(TransactionType.SELL, LocalDate.now(), 20.0, 0.0008, 0.1, FeeType.DOLLAR),
        Transaction(TransactionType.BUY, LocalDate.now(), 5.0, 0.0005, 0.0001, FeeType.COIN),
        Transaction(TransactionType.BUY, LocalDate.now(), 20.0, 0.00012, 0.1, FeeType.DOLLAR),
        Transaction(TransactionType.SELL, LocalDate.now(), 15.0, 0.0001, 0.1, FeeType.DOLLAR),
        Transaction(TransactionType.BUY, LocalDate.now(), 20.0, 0.00022, 0.1, FeeType.DOLLAR),
        Transaction(TransactionType.SEND, LocalDate.now(), 20.0, 0.00022, 0.1, FeeType.DOLLAR),
        Transaction(TransactionType.MOVE, LocalDate.now(), 20.0, 0.00022, 0.1, FeeType.DOLLAR),
        Transaction(
            TransactionType.RECEIVE,
            LocalDate.now(),
            20.0,
            0.00022,
            0.1,
            FeeType.DOLLAR
        )
    )
}