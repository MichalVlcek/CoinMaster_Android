package com.example.coinapp.ui.transaction

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.coinapp.TransactionManageActivity
import com.example.coinapp.data.Coin
import com.example.coinapp.data.FeeType
import com.example.coinapp.data.Transaction
import com.example.coinapp.data.TransactionType
import com.example.coinapp.databinding.TransactionFormFragmentBinding
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.time.LocalDate

abstract class AbstractTransactionManageFragment : Fragment() {

    protected var coin: Coin? = null
    protected var transaction: Transaction? = null

    lateinit var viewModel: TransactionManageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            coin = it.getParcelable(TransactionManageActivity.COIN)
            transaction = it.getParcelable(TransactionManageActivity.TRANSACTION)
        }
    }

    protected fun showDatePickerDialog(label: TextView) {
        val newFragment = DatePickerFragment(label)
        newFragment.show(parentFragmentManager, "datePicker")
    }

    protected fun updateTransaction(
        form: TransactionFormFragmentBinding,
        coinId: String?,
        originalTransaction: Transaction? = null
    ) {
        try {
            val transaction = buildTransaction(form, coinId, originalTransaction)
            insertIntoDatabase(transaction)

            requireActivity().finish() // Finishes the activity and goes back
        } catch (e: IOException) {
            Snackbar.make(
                requireContext(),
                requireView(),
                "Operation Failed, check that inputs are not empty and contains valid values.",
                4000
            ).show()
        } catch (e: Exception) {
            Snackbar.make(
                requireContext(),
                requireView(),
                "Operation Failed from unknown reasons.",
                4000
            ).show()
        }
    }

    private fun buildTransaction(
        form: TransactionFormFragmentBinding,
        coinId: String?,
        transaction: Transaction? = null
    ): Transaction {
        //TODO implement Fee type

        try {
            val type = TransactionType.values()[form.transactionType.selectedItemPosition]
            val coinPrice = form.transactionPrice.text.toString().toDouble()
            val amount = form.transactionAmount.text.toString().toDouble()
            val fee = form.transactionFee.text.toString().toDouble()
            val date = LocalDate.parse(form.transactionDate.text)
            val description = form.transactionDescription.text.toString()

            return Transaction(
                id = transaction?.id ?: 0,
                coinId = coinId,
                type = type,
                date = date,
                cost = coinPrice * amount + fee,
                amount = amount,
                fee = fee,
                feeType = FeeType.DOLLAR,
                description = description
            )

        } catch (e: Exception) {
            throw IOException(e)
        }
    }

    protected abstract fun insertIntoDatabase(transaction: Transaction)
}