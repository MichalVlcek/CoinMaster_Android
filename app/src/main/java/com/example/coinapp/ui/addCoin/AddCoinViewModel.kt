package com.example.coinapp.ui.addCoin

import android.app.Application
import androidx.lifecycle.*
import com.example.coinapp.api.ApiService
import com.example.coinapp.data.repositories.CoinRepository
import com.example.coinapp.data.repositories.UserRepository
import com.example.coinapp.model.Coin
import com.example.coinapp.model.User
import com.example.coinapp.utils.UserUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddCoinViewModel(application: Application) : AndroidViewModel(application) {

    private val coinRepository by lazy { CoinRepository.getInstance(application) }
    private val userRepository by lazy { UserRepository.getInstance(application) }

    private val _items = MutableLiveData<List<Coin>>().apply {
        value = emptyList()
    }

    val items: LiveData<List<Coin>>
        get() = _items

    private val _coinCount = MutableLiveData<Int>().apply {
        viewModelScope.launch {
            postValue(coinRepository.countCoinsForUser())
        }
    }

    private val _loggedUser = MutableLiveData<User>().apply {
        viewModelScope.launch {
            postValue(userRepository.getUserById(UserUtils.getLoggedUserId(application)))
        }
    }

    val coinCountAndLoggedUserLiveData = MediatorLiveData<Pair<Int, User>>().apply {
        var coinCount: Int? = null
        var loggedUser: User? = null

        fun update() {
            val localCoinCount = coinCount
            val localLoggedUser = loggedUser
            if (localCoinCount != null && localLoggedUser != null)
                this.value = Pair(localCoinCount, localLoggedUser)
        }

        addSource(_coinCount) {
            coinCount = it
            update()
        }
        addSource(_loggedUser) {
            loggedUser = it
            update()
        }
    }

    /**
     * Clears [_items]
     */
    fun clearItems() {
        _items.value = emptyList()
    }

    /**
     * Adds [coin] to database
     */
    suspend fun addCoin(coin: Coin) {
        val request = viewModelScope.async(Dispatchers.IO) {
            coinRepository.insertCoin(coin)
        }
        request.await()
    }

    /**
     * Calls ApiService for values and updates [_items]
     * Request is awaited in case of exceptions
     */
    suspend fun fetchData() {
        val request = viewModelScope.async() {
            _items.postValue(ApiService.getInstance().getCoins())
        }
        request.await()
    }
}