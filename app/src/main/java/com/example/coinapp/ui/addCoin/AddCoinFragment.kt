package com.example.coinapp.ui.addCoin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinapp.R
import com.example.coinapp.data.Coin
import com.example.coinapp.databinding.AddCoinFragmentBinding
import com.example.coinapp.databinding.HomeScreenFragmentBinding
import com.example.coinapp.ui.homeScreen.HomeScreenAdapter
import com.example.coinapp.ui.homeScreen.HomeScreenViewModel

class AddCoinFragment : Fragment() {

    companion object {
        fun newInstance() = AddCoinFragment()
    }

    private lateinit var listAdapter: AddCoinAdapter
    private lateinit var viewModel: AddCoinViewModel

    private var _binding: AddCoinFragmentBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddCoinFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddCoinViewModel::class.java)

        listAdapter = AddCoinAdapter(binding.switcher) { coin -> adapterOnClick(coin) }

        binding.watchedCoinsList.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = listAdapter
        }

        viewModel.items.observe(
            viewLifecycleOwner,
            {
                listAdapter.coins = it
            }
        )

        binding.button.setOnClickListener { refreshData() }
    }

    private fun adapterOnClick(coin: Coin) {
    }

    private fun refreshData() {
        viewModel.fetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}