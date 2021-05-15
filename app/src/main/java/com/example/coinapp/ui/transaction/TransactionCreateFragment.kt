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
import com.example.coinapp.databinding.TransactionCreateFragmentBinding
import com.example.coinapp.ui.coinDetail.CoinDetailViewModel
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
        //TODO: WHEN database will be used, this won't be necessary probably? maybe i will update database inside viewModel
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}