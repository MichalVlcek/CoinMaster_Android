package com.example.coinapp.ui.coinDetail.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coinapp.CoinDetailActivity
import com.example.coinapp.data.Coin
import com.example.coinapp.databinding.CoinDetailTransactionFragmentBinding

class TransactionFragment : Fragment() {

    companion object {
        fun newInstance(coin: Coin) =
            TransactionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CoinDetailActivity.COIN, coin)
                }
            }
    }

    private var coin: Coin? = null

    private var _binding: CoinDetailTransactionFragmentBinding? = null
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
        _binding = CoinDetailTransactionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
}