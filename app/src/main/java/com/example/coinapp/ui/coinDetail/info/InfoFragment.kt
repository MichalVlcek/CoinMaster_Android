package com.example.coinapp.ui.coinDetail.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coinapp.data.Coin
import com.example.coinapp.databinding.CoinDetailInfoFragmentBinding
import com.example.coinapp.ui.coinDetail.CoinDetailViewModel

class InfoFragment : Fragment() {

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
        val viewModel = ViewModelProvider(requireActivity()).get(CoinDetailViewModel::class.java)

        viewModel.coin.observe(
            viewLifecycleOwner,
            {
                bindData(it)
            }
        )
    }

    private fun bindData(coin: Coin?) {
        coin?.let {
            binding.rank.text = "#${it.rank}"
            binding.price.text = "${it.price}$"
            binding.marketCap.text = "${it.marketCap.toBigDecimal().toPlainString()}$"
            binding.supply.text = it.supply.toBigDecimal().toPlainString()
        }
    }
}