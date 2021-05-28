package com.example.coinapp.ui.addCoin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinapp.databinding.AddCoinFragmentBinding
import com.example.coinapp.model.Coin
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

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

        val swipeContainer = binding.swipeContainer
        binding.swipeContainer.setOnRefreshListener {
            viewModel.clearItems()
            refreshData()
            swipeContainer.isRefreshing = false
        }

        viewModel.items.observe(
            viewLifecycleOwner,
            {
                listAdapter.coins = it
            }
        )

        refreshData()
    }

    /**
     * Adds [coin] to database and ends this activity
     */
    private fun adapterOnClick(coin: Coin) {
        lifecycleScope.launch {
            try {
                viewModel.addCoin(coin)
                requireActivity().finish()
            } catch (e: Exception) {
                Snackbar.make(
                    requireContext(),
                    requireView(),
                    "Something bad happened, can't add coin.",
                    4000
                ).show()
            }
        }
    }

    /**
     * Calls function from viewModel to refresh data
     * When Exception is caught, SnackBar is shown
     */
    private fun refreshData() {
        lifecycleScope.launch {
            try {
                viewModel.fetchData()
            } catch (e: Exception) {
                Snackbar.make(
                    requireContext(),
                    requireView(),
                    "Data loading failed, check your internet connection.",
                    4000
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}