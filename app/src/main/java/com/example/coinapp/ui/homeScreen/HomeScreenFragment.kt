package com.example.coinapp.ui.homeScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinapp.AddCoinActivity
import com.example.coinapp.CoinDetailActivity
import com.example.coinapp.CoinDetailActivity.Companion.COIN
import com.example.coinapp.data.Coin
import com.example.coinapp.data.Transaction
import com.example.coinapp.databinding.HomeScreenFragmentBinding
import com.example.coinapp.helper.CoinUtility
import com.example.coinapp.helper.StringOperations

class HomeScreenFragment : Fragment() {

    companion object {
        fun newInstance() = HomeScreenFragment()
    }

    private var firstVisit = false // Used as workaround for missing onRestart() in fragments

    private lateinit var listAdapter: HomeScreenAdapter
    private lateinit var viewModel: HomeScreenViewModel

    private var _binding: HomeScreenFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeScreenViewModel::class.java)

        listAdapter = HomeScreenAdapter(
            binding.switcher,
            binding.emptySwitcher
        ) { coin -> adapterOnClick(coin) }

        binding.watchedCoinsList.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = listAdapter
        }

        val swipeContainer = binding.swipeContainer
        binding.swipeContainer.setOnRefreshListener {
            viewModel.clearCoinList()
            updateData()
            swipeContainer.isRefreshing = false
        }

        viewModel.coins.observe(
            viewLifecycleOwner,
            {
                listAdapter.coins = it
                bindDataToOverview(viewModel.transactions.value, it)
            }
        )

        viewModel.transactions.observe(
            viewLifecycleOwner,
            {
                listAdapter.transactions = it
                bindDataToOverview(it, viewModel.coins.value)
            }
        )

        binding.fab.setOnClickListener { openAddCoinActivity() }

        firstVisit = true
        updateData()
    }


    override fun onResume() {
        super.onResume()

        if (!firstVisit) {
            getData()
        }
    }

    override fun onPause() {
        super.onPause()

        firstVisit = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData() {
        viewModel.getCoinsFromDB()
        viewModel.getTransactions()
    }

    private fun updateData() {
        viewModel.updateCoins()
        viewModel.getTransactions()
    }

    private fun openAddCoinActivity() {
        val intent = Intent(context, AddCoinActivity()::class.java)
        startActivity(intent)
    }

    private fun adapterOnClick(coin: Coin) {
        val intent = Intent(context, CoinDetailActivity::class.java)
        intent.putExtra(COIN, coin)
        startActivity(intent)
    }

    private fun bindDataToOverview(transactions: List<Transaction>?, coins: List<Coin>?) {
        val totalHoldings =
            coins?.map { coin ->
                CoinUtility.countHoldingsValue(transactions?.filter { t ->
                    t.coinId == coin.id
                } ?: emptyList(), coin.price)
            }?.sum() ?: 0.0
        binding.totalHoldings.text = StringOperations.formatCurrency(totalHoldings)

        //TODO finish this method when the CoinGecko wrapper is fixed
//        lifecycleScope.launch {
//            //TODO handle exception
//
//            val request = lifecycleScope.async(Dispatchers.IO) {
//                return@async coins?.map { coin ->
//                    CoinUtility.countHoldingsValue(transactions?.filter { t ->
//                        t.coinId == coin.id
//                    } ?: emptyList(),
//                        ApiService.getInstance()
//                            .getHistoricalCoinPrice(coin.id, LocalDate.now().minusDays(7)))
//                }?.sum() ?: 0.0
//            }
//
//            val holdingsBefore = request.await()
//
//            binding.changeValue.setTextAndColor(
//                StringOperations.formatCurrency(totalHoldings - holdingsBefore),
//                totalHoldings - holdingsBefore
//            )
//
//            binding.changePercentage.setTextAndColor(
//                StringOperations.formatPercentage((totalHoldings / holdingsBefore / 100) - 1),
//                (totalHoldings / holdingsBefore / 100) - 1
//            )
//        }
    }
}