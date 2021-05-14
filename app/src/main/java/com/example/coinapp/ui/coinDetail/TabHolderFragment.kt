package com.example.coinapp.ui.coinDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.coinapp.databinding.CoinDetailTabHolderFragmentBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabHolderFragment : Fragment() {

    companion object {
        fun newInstance() = TabHolderFragment()
    }

    private lateinit var pagerAdapter: SectionsPagerAdapter
    private lateinit var viewPager: ViewPager2

    private var _binding: CoinDetailTabHolderFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CoinDetailTabHolderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
}