package com.example.coinapp.ui.homeScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinapp.AddCoinActivity
import com.example.coinapp.CoinDetailActivity
import com.example.coinapp.CoinDetailActivity.Companion.COIN
import com.example.coinapp.R
import com.example.coinapp.databinding.HomeScreenFragmentBinding
import com.example.coinapp.model.Coin
import com.example.coinapp.model.Transaction
import com.example.coinapp.utils.CoinUtility
import com.example.coinapp.utils.StringOperations
import com.example.coinapp.utils.TextViewOperations.setTextAndColor
import kotlinx.coroutines.launch
import java.time.LocalDate

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
//                bindDataToOverview(it, viewModel.coins.value)
            }
        )

        binding.intervalButtonGroup.setOnCheckedChangeListener { _, _ ->
            bindDataToOverview(viewModel.transactions.value, viewModel.coins.value)
        }

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

    private fun intervalFromGroup(group: RadioGroup): Long {
        return when (group.checkedRadioButtonId) {
            binding.radio1d.id -> 1
            binding.radio7d.id -> 7
            binding.radio30d.id -> 30
            binding.radio90d.id -> 90
            binding.radio1y.id -> 365
            else -> 0
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindDataToOverview(
        transactionsNullable: List<Transaction>?,
        coinsNullable: List<Coin>?
    ) {
        val transactions = transactionsNullable ?: emptyList()
        val coins = coinsNullable ?: emptyList()
        val totalHoldings = CoinUtility.countHoldingsValue(transactions, coins)
        val interval = intervalFromGroup(binding.intervalButtonGroup)
        val compareDate = LocalDate.now().minusDays(interval)

        // set text to text views
        binding.totalHoldings.text = StringOperations.formatCurrency(totalHoldings)
        binding.changeHeading.text = "$interval ${getString(R.string.change_heading)}"
        binding.highHeading.text = "$interval ${getString(R.string.high_heading)}"
        binding.lowHeading.text = "$interval ${getString(R.string.low_heading)}"

        lifecycleScope.launch {

            val holdingsHistorical = CoinUtility.countHoldingsValueHistorical(
                transactions,
                viewModel.getHistoricalPrices(compareDate),
                compareDate
            )

            val valueChange = totalHoldings - holdingsHistorical
            binding.changeValue.setTextAndColor(
                StringOperations.formatCurrency(valueChange),
                valueChange
            )

            val valueChangePercent = (totalHoldings / holdingsHistorical) - 1
            binding.changePercentage.setTextAndColor(
                StringOperations.formatPercentage(valueChangePercent),
                valueChangePercent
            )
        }
    }
}