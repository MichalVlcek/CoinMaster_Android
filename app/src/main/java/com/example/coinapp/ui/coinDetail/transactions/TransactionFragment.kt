package com.example.coinapp.ui.coinDetail.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coinapp.databinding.CoinDetailTransactionFragmentBinding

class TransactionFragment : Fragment() {

    private var _binding: CoinDetailTransactionFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CoinDetailTransactionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}