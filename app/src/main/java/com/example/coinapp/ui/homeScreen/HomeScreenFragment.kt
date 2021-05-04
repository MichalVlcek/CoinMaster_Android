package com.example.coinapp.ui.homeScreen

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinapp.MainActivity
import com.example.coinapp.data.Coin
import com.example.coinapp.databinding.HomeScreenFragmentBinding

class HomeScreenFragment : Fragment() {

    companion object {
        fun newInstance() = HomeScreenFragment()
    }

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

        viewModel.items.observe(
            viewLifecycleOwner,
            {
                listAdapter.coins = it
            }
        )

        binding.fab.setOnClickListener { openAddCoinActivity() }
    }

    private fun openAddCoinActivity() {
        val intent = Intent(context, MainActivity()::class.java)
        startActivity(intent)
    }

    private fun adapterOnClick(coin: Coin) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}