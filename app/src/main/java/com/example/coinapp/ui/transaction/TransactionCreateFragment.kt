package com.example.coinapp.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coinapp.CoinDetailActivity
import com.example.coinapp.R
import com.example.coinapp.data.Coin
import com.example.coinapp.data.FeeType
import com.example.coinapp.data.Transaction
import com.example.coinapp.data.TransactionType
import com.example.coinapp.databinding.TransactionCreateFragmentBinding
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.time.LocalDate

class TransactionCreateFragment : Fragment() {

    companion object {
        fun newInstance(coin: Coin?) = TransactionCreateFragment().apply {
            arguments = Bundle().apply {
                putParcelable(CoinDetailActivity.COIN, coin)
            }
        }
    }

    private var coin: Coin? = null

    private lateinit var viewModel: TransactionCreateViewModel

    private var _binding: TransactionCreateFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            coin = it.getParcelable(CoinDetailActivity.COIN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TransactionCreateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TransactionCreateViewModel::class.java)
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

        binding.transactionButtonAdd.setOnClickListener { addTransaction(coin?.id) }
    }

    private fun showDatePickerDialog(label: TextView) {
        val newFragment = DatePickerFragment(label)
        newFragment.show(parentFragmentManager, "datePicker")
    }

    private fun addTransaction(coinId: String?) {
        try {
            val transaction = buildTransaction(coinId)
            insertIntoDatabase(transaction)

            activity?.finish() // Finishes the activity and goes back
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

    private fun buildTransaction(coinId: String?): Transaction {
        //TODO implement Fee type
        val form = binding.form

        try {
            val type = TransactionType.values()[form.transactionType.selectedItemPosition]
            val coinPrice = form.transactionPrice.text.toString().toDouble()
            val amount = form.transactionAmount.text.toString().toDouble()
            val fee = form.transactionFee.text.toString().toDouble()
            val date = LocalDate.parse(form.transactionDate.text)
            val description = form.transactionDescription.text.toString()

            return Transaction(
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
        viewModel.createNewTransaction(transaction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}