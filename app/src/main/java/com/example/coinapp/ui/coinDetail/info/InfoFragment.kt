package com.example.coinapp.ui.coinDetail.info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coinapp.TransactionManageActivity
import com.example.coinapp.data.Coin
import com.example.coinapp.data.Transaction
import com.example.coinapp.databinding.CoinDetailInfoFragmentBinding
import com.example.coinapp.helper.CoinUtility
import com.example.coinapp.helper.StringOperations
import com.example.coinapp.helper.TextViewOperations.setTextAndColor
import com.example.coinapp.ui.coinDetail.CoinDetailViewModel

class InfoFragment : Fragment() {

    private lateinit var viewModel: CoinDetailViewModel

    private var _binding: CoinDetailInfoFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CoinDetailInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(CoinDetailViewModel::class.java)

        viewModel.transactions.observe(
            viewLifecycleOwner,
            {
                bindData(it, viewModel.coin.value)
            }
        )

        binding.unwatchButton.setOnClickListener { unwatchCoin() }
        binding.infoAddTransactionButton.setOnClickListener { openAddTransaction() }
    }

    private fun unwatchCoin() {
        viewModel.unwatchCoin()
        requireActivity().finish()
    }

    private fun openAddTransaction() {
        val intent = Intent(context, TransactionManageActivity::class.java)
        intent.putExtra(TransactionManageActivity.COIN, viewModel.coin.value)
        startActivity(intent)
    }

    /**
     * Binds data to TextViews on screen
     */
    private fun bindData(transactions: List<Transaction>, coin: Coin?) {
        coin?.let {
            // Holdings info
            binding.holdings.text =
                StringOperations.formatCurrency(CoinUtility.countHoldings(transactions), coin)
            binding.value.text =
                StringOperations.formatCurrency(CoinUtility.countHoldingsValue(transactions, coin))
            binding.buyPrice.text =
                StringOperations.formatCurrency(CoinUtility.countAverageTransactionCost(transactions))
            binding.deposit.text =
                StringOperations.formatCurrency(CoinUtility.countTotalCost(transactions))

            val percentChange = CoinUtility.countPercentageChange(transactions, coin)
            binding.percentChange.setTextAndColor(
                StringOperations.formatPercentage(percentChange),
                percentChange
            )

            val profitLoss = CoinUtility.countProfitOrLoss(transactions, coin)
            binding.profitLoss.setTextAndColor(
                StringOperations.formatCurrency(profitLoss),
                profitLoss
            )

            // Coin info
            binding.rank.text = StringOperations.formatRank(it.rank)
            binding.price.text = StringOperations.formatCurrency(it.price)
            binding.marketCap.text = StringOperations.formatCurrency(it.marketCap)
            binding.supply.text = StringOperations.formatCurrency(coin.supply, coin)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}