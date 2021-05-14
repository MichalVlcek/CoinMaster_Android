package com.example.coinapp.ui.coinDetail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.coinapp.R
import com.example.coinapp.ui.coinDetail.info.InfoFragment
import com.example.coinapp.ui.coinDetail.transactions.TransactionFragment

val TAB_TITLES = arrayOf(
    R.string.coin_info,
    R.string.transactions
)

/**
 * A [FragmentStateAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val fragments = arrayOf(
        InfoFragment(),
        TransactionFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}