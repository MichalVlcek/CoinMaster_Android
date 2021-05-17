package com.example.coinapp.ui.coinDetail.transactions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinapp.TransactionCreateActivity
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

        listAdapter = TransactionsAdapter(
            binding.switcher,
            binding.emptySwitcher,
            coin
        ) { transaction ->
            openEditTransaction(transaction)
        }

        binding.transactionsList.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = listAdapter
        }

        val swipeContainer = binding.swipeContainer
        binding.swipeContainer.setOnRefreshListener {
            viewModel.clearTransactions()
            refreshData()
            swipeContainer.isRefreshing = false
        }

        viewModel.transactions.observe(
            viewLifecycleOwner,
            {
                listAdapter.transactions = it
            }
        )

        binding.addTransactionButton.setOnClickListener { openAddTransaction() }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        viewModel.getTransactionsByCoinId(viewModel.coin.value?.id)
    }

    private fun openAddTransaction() {
        val intent = Intent(context, TransactionCreateActivity::class.java)
        intent.putExtra(TransactionCreateActivity.COIN, viewModel.coin.value)
        startActivity(intent)
    }

    private fun openEditTransaction(transaction: Transaction) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}