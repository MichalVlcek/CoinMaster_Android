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
import com.example.coinapp.data.FeeType
import com.example.coinapp.data.Transaction
import com.example.coinapp.data.TransactionType
import com.example.coinapp.databinding.TransactionCreateFragmentBinding
import com.example.coinapp.ui.coinDetail.CoinDetailViewModel
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate

class TransactionCreateFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionCreateFragment()
    }

    private lateinit var viewModel: CoinDetailViewModel

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
        viewModel = ViewModelProvider(this).get(CoinDetailViewModel::class.java)

        val typeSpinner = binding.transactionType

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.transaction_types_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.transaction_type_spinner)
            typeSpinner.adapter = adapter
        }

        val dateLabel = binding.transactionDate
        dateLabel.text = LocalDate.now().toString()
        binding.transactionDate.setOnClickListener { showDatePickerDialog(dateLabel) }

        binding.transactionButtonAdd.setOnClickListener { addTransaction() }
    }

    private fun showDatePickerDialog(label: TextView) {
        val newFragment = DatePickerFragment(label)
        newFragment.show(parentFragmentManager, "datePicker")
    }

    private fun addTransaction() {
        //TODO implement FEE type
        try {
            val type = TransactionType.values()[binding.transactionType.selectedItemPosition]
            val coinPrice = binding.transactionPrice.text.toString().toDouble()
            val amount = binding.transactionAmount.text.toString().toDouble()
            val fee = binding.transactionFee.text.toString().toDouble()
            val date = LocalDate.parse(binding.transactionDate.text)
            val description = binding.transactionDescription.text.toString()

            val transaction = Transaction(
                type,
                date,
                coinPrice * amount + fee,
                amount,
                fee,
                FeeType.DOLLAR,
                description
            )

            viewModel.createNewTransaction(transaction)

            activity?.finish() // Finishes the activity and goes back
        } catch (e: Exception) {
            Snackbar.make(
                requireContext(),
                requireView(),
                "Operation Failed, check that inputs are not empty and contains valid values.",
                4000
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}