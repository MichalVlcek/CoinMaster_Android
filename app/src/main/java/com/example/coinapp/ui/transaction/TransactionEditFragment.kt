package com.example.coinapp.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coinapp.R
import com.example.coinapp.TransactionManageActivity
import com.example.coinapp.data.Coin
import com.example.coinapp.data.FeeType
import com.example.coinapp.data.Transaction
import com.example.coinapp.data.TransactionType
import com.example.coinapp.databinding.TransactionEditFragmentBinding
import com.example.coinapp.databinding.TransactionFormFragmentBinding
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.time.LocalDate

class TransactionEditFragment : Fragment() {

    companion object {
        fun newInstance(coin: Coin?, transaction: Transaction?) = TransactionEditFragment().apply {
            arguments = Bundle().apply {
                putParcelable(TransactionManageActivity.COIN, coin)
                putParcelable(TransactionManageActivity.TRANSACTION, transaction)
            }
        }
    }

    private var coin: Coin? = null
    lateinit var transaction: Transaction

    private lateinit var viewModel: TransactionManageViewModel

    private var _binding: TransactionEditFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            coin = it.getParcelable(TransactionManageActivity.COIN)
            transaction = it.getParcelable(TransactionManageActivity.TRANSACTION)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TransactionEditFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TransactionManageViewModel::class.java)
        val form = binding.form

        val typeSpinner = form.transactionType

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.transaction_types_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.transaction_type_spinner)
            typeSpinner.adapter = adapter
        }

        bindData(form, transaction)

        form.transactionDate.setOnClickListener { showDatePickerDialog(form.transactionDate) }

        binding.transactionUpdateButton.setOnClickListener { updateTransaction(form, coin?.id) }
        binding.transactionDeleteButton.setOnClickListener { deleteTransaction(transaction) }
    }

    private fun bindData(form: TransactionFormFragmentBinding, transaction: Transaction) {
        form.transactionType.setSelection(TransactionType.values().indexOf(transaction.type))
        form.transactionPrice.setText(
            ((transaction.cost - transaction.fee) / transaction.amount).toString() // TODO division by zero
        )
        form.transactionAmount.setText(transaction.amount.toString())
        form.transactionFee.setText(transaction.fee.toString())
        form.transactionDate.text = transaction.date.toString()
        form.transactionDescription.setText(transaction.description)
    }

    private fun showDatePickerDialog(label: TextView) {
        val newFragment = DatePickerFragment(label)
        newFragment.show(parentFragmentManager, "datePicker")
    }

    private fun updateTransaction(form: TransactionFormFragmentBinding, coinId: String?) {
        try {
            val transaction = buildTransaction(form, coinId)
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
        coinId: String?
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
                id = transaction.id,
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

    private fun insertIntoDatabase(transaction: Transaction) {
        viewModel.updateTransaction(transaction)
    }

    private fun deleteTransaction(transaction: Transaction) {
        viewModel.deleteTransaction(transaction)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}