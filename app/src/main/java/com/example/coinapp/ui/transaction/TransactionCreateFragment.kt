package com.example.coinapp.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.coinapp.R
import com.example.coinapp.TransactionManageActivity
import com.example.coinapp.data.Coin
import com.example.coinapp.data.Transaction
import com.example.coinapp.databinding.TransactionCreateFragmentBinding
import java.time.LocalDate

class TransactionCreateFragment : AbstractTransactionManageFragment() {

    companion object {
        fun newInstance(coin: Coin?) = TransactionCreateFragment().apply {
            arguments = Bundle().apply {
                putParcelable(TransactionManageActivity.COIN, coin)
            }
        }
    }

    private var _binding: TransactionCreateFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TransactionCreateFragmentBinding.inflate(inflater, container, false)
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

        val dateLabel = binding.form.transactionDate
        dateLabel.text = LocalDate.now().toString()
        form.transactionDate.setOnClickListener { showDatePickerDialog(dateLabel) }

        binding.transactionButtonAdd.setOnClickListener { updateTransaction(form, coin?.id) }
    }

    override fun insertIntoDatabase(transaction: Transaction) {
        viewModel.createNewTransaction(transaction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}