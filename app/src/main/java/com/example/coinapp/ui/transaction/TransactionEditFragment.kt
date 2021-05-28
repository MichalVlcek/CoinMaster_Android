package com.example.coinapp.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.coinapp.R
import com.example.coinapp.TransactionManageActivity
import com.example.coinapp.databinding.TransactionEditFragmentBinding
import com.example.coinapp.databinding.TransactionFormFragmentBinding
import com.example.coinapp.model.Coin
import com.example.coinapp.model.Transaction
import com.example.coinapp.model.enums.TransactionType

class TransactionEditFragment : AbstractTransactionManageFragment() {

    companion object {
        fun newInstance(coin: Coin?, transaction: Transaction?) = TransactionEditFragment().apply {
            arguments = Bundle().apply {
                putParcelable(TransactionManageActivity.COIN, coin)
                putParcelable(TransactionManageActivity.TRANSACTION, transaction)
            }
        }
    }

    private var _binding: TransactionEditFragmentBinding? = null
    private val binding
        get() = _binding!!

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

        bindData(form, transaction!!)

        form.transactionDate.setOnClickListener { showDatePickerDialog(form.transactionDate) }

        binding.transactionUpdateButton.setOnClickListener {
            updateTransaction(
                form,
                coin?.id,
                transaction
            )
        }
        binding.transactionDeleteButton.setOnClickListener { deleteTransaction(transaction!!) }
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

    override fun insertIntoDatabase(transaction: Transaction) {
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