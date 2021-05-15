package com.example.coinapp.ui.coinDetail.info

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coinapp.data.Coin
import com.example.coinapp.databinding.CoinDetailInfoFragmentBinding
import com.example.coinapp.helper.StringOperations
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

        viewModel.coin.observe(
            viewLifecycleOwner,
            {
                bindData(it)
            }
        )
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(coin: Coin?) {
        coin?.let {
            binding.holdings.text = StringOperations.formatCurrency(viewModel.countHoldings(), coin)
            binding.value.text = StringOperations.formatCurrency(viewModel.countHoldingsValue())
            binding.buyPrice.text =
                StringOperations.formatCurrency(viewModel.countAverageTransactionCost())
            binding.deposit.text = StringOperations.formatCurrency(viewModel.countTotalCost())
            binding.percentChange.text =
                StringOperations.formatPercentage(viewModel.countPercentageChange())
            binding.profitLoss.text = StringOperations.formatCurrency(viewModel.countProfitOrLoss())

            //Coin info
            binding.rank.text = "#${it.rank}"
            binding.price.text = StringOperations.formatCurrency(it.price)
            binding.marketCap.text = StringOperations.formatCurrency(it.marketCap)
            binding.supply.text = StringOperations.formatCurrency(coin.supply, coin)
        }
    }
}