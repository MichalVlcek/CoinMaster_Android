package com.example.coinapp.ui.coinDetail.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinapp.data.Transaction
import com.example.coinapp.databinding.TransactionsFragmentBinding
import com.example.coinapp.ui.coinDetail.CoinDetailViewModel

class TransactionsFragment : Fragment() {

    private lateinit var listAdapter: TransactionsAdapter
    private lateinit var viewModel: CoinDetailViewModel

    private var _binding: TransactionsFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TransactionsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(CoinDetailViewModel::class.java)

        val coin = viewModel.coin.value

        listAdapter = TransactionsAdapter(binding.switcher, coin) { transaction ->
            adapterOnClick(transaction)
        }

        binding.transactionsList.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = listAdapter
        }

        viewModel.transactions.observe(
            viewLifecycleOwner,
            {
                listAdapter.transactions = it
            }
        )
    }

    private fun adapterOnClick(transaction: Transaction) {
    }
}