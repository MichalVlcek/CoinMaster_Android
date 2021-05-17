package com.example.coinapp.ui.coinDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.coinapp.CoinDetailActivity
import com.example.coinapp.data.Coin
import com.example.coinapp.databinding.CoinDetailTabHolderFragmentBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CoinDetailFragment : Fragment() {

    companion object {
        fun newInstance(coin: Coin?) =
            CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CoinDetailActivity.COIN, coin)
                }
            }
    }

    private var coin: Coin? = null

    private lateinit var viewModel: CoinDetailViewModel

    private lateinit var pagerAdapter: SectionsPagerAdapter
    private lateinit var viewPager: ViewPager2

    private var _binding: CoinDetailTabHolderFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            coin = it.getParcelable(CoinDetailActivity.COIN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CoinDetailTabHolderFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.getTransactions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(CoinDetailViewModel::class.java)

        viewModel.setCoin(coin!!)

        pagerAdapter = SectionsPagerAdapter(this)

        viewPager = binding.viewPager

        viewPager.apply {
            adapter = pagerAdapter
        }

        val tabLayout: TabLayout = binding.tabs
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}