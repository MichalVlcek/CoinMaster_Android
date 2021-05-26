package com.example.coinapp.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coinapp.data.User
import com.example.coinapp.data.UserRepository
import kotlinx.coroutines.async

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _signedUser = MutableLiveData<User>()

    val signedUser: LiveData<User>
        get() = _signedUser

    private val userRepository by lazy { UserRepository.getInstance(application) }

    suspend fun loginUser(email: String, password: String) {
        val request = viewModelScope.async {
            _signedUser.postValue(userRepository.retrieveUser(email, password))
        }

        return request.await()
    }
}