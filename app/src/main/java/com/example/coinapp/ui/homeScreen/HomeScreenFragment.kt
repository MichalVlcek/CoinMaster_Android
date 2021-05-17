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
import com.example.coinapp.databinding.HomeScreenFragmentBinding

class HomeScreenFragment : Fragment() {

    companion object {
        fun newInstance() = HomeScreenFragment()
    }

    private var firstVisit = true // Used as workaround for missing onRestart() in fragments

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

        listAdapter = HomeScreenAdapter(binding.switcher) { coin -> adapterOnClick(coin) }

        binding.watchedCoinsList.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = listAdapter
        }

        val swipeContainer = binding.swipeContainer
        binding.swipeContainer.setOnRefreshListener {
            viewModel.clearData()
            updateData()
            swipeContainer.isRefreshing = false
        }

        viewModel.items.observe(
            viewLifecycleOwner,
            {
                listAdapter.coins = it
            }
        )

        binding.fab.setOnClickListener { openAddCoinActivity() }

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

    private fun getData() {
        viewModel.getData()
    }

    private fun updateData() {
        viewModel.updateData()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}