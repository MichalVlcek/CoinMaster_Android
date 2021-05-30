package com.example.coinapp.ui.addCoin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinapp.R
import com.example.coinapp.data.ConfigValues
import com.example.coinapp.databinding.AddCoinFragmentBinding
import com.example.coinapp.exceptions.MaximumCoinsException
import com.example.coinapp.model.Coin
import com.example.coinapp.model.User
import com.example.coinapp.utils.TextViewOperations.setTextAndColor
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

        viewModel.coinCountAndLoggedUserLiveData.observe(
            viewLifecycleOwner,
            { (count, user) ->
                setUserType(user)
                setCurrentCoinsCount(count, user)
            }
        )

        binding.coinSearch.setOnQueryTextListener(listAdapter)

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
            } catch (e: MaximumCoinsException) {
                Snackbar.make(
                    requireContext(),
                    requireView(),
                    "You can't add more coins, you must upgrade to Premium.",
                    4000
                ).show()
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

    @SuppressLint("SetTextI18n")
    private fun setUserType(user: User) {
        binding.userType.setTextAndColor(user, requireContext())
    }

    @SuppressLint("SetTextI18n")
    private fun setCurrentCoinsCount(coinCount: Int, user: User) {
        val outOf = if (user.premium) "∞" else ConfigValues.BASiC_USER_MAX_COINS

        binding.watchedCoins.text = "${getString(R.string.added_coins)} $coinCount/${outOf}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}