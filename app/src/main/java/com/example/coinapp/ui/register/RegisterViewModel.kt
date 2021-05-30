package com.example.coinapp.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coinapp.data.repositories.UserRepository
import com.example.coinapp.model.User
import com.example.coinapp.utils.UserUtils
import kotlinx.coroutines.async

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val _registeredUser = MutableLiveData<User>()

    val registeredUser: LiveData<User>
        get() = _registeredUser

    private val userRepository by lazy { UserRepository.getInstance(application) }

    suspend fun registerUser(email: String, password: String, premium: Boolean) {
        val request = viewModelScope.async {
            val user = User(
                email = email,
                password = UserUtils.hashPassword(password),
                premium = premium
            )
            userRepository.registerUser(user)
            // Retrieving user from database, because I need to get newly generated id
            _registeredUser.postValue(userRepository.retrieveUser(email, password))
        }

        return request.await()
    }
}