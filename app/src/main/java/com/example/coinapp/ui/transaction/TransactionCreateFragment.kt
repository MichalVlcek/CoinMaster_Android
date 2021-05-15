package com.example.coinapp.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coinapp.databinding.TransactionCreateFragmentBinding

class TransactionCreateFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionCreateFragment()
    }

    private var _binding: TransactionCreateFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TransactionCreateFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}