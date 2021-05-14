package com.example.coinapp.ui.coinDetail.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coinapp.CoinDetailActivity.Companion.COIN
import com.example.coinapp.data.Coin
import com.example.coinapp.databinding.CoinDetailInfoFragmentBinding

class InfoFragment : Fragment() {

    companion object {
        fun newInstance(coin: Coin) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(COIN, coin)
                }
            }
    }

    private var coin: Coin? = null

    private var _binding: CoinDetailInfoFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            coin = it.getParcelable(COIN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CoinDetailInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.sectionLabel.text = coin?.name ?: "FUCK YOU"
    }
}