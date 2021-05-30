package com.example.coinapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.coinapp.HomeScreenActivity
import com.example.coinapp.RegisterActivity
import com.example.coinapp.databinding.LoginFragmentBinding
import com.example.coinapp.exceptions.WrongCredentialsException
import com.example.coinapp.utils.UserUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    private var _binding: LoginFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.signedUser.observe(
            viewLifecycleOwner,
            {
                UserUtils.storeLoggedUserId(it.id, requireContext())
                signIn()
            }
        )

        binding.signInButton.setOnClickListener { login() }
        binding.signUpSwitch.setOnClickListener { switchToRegister() }
    }

    private fun login() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        // Error check
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.isEmpty()) {
            Snackbar.make(
                requireContext(),
                requireView(),
                "Email must be valid and password can't be empty.",
                4000
            ).show()

            return
        }

        lifecycleScope.launch {
            try {
                viewModel.loginUser(email, password)
            } catch (e: WrongCredentialsException) {
                Snackbar.make(
                    requireContext(),
                    requireView(),
                    "You typed wrong credentials, either email or password is wrong!",
                    4000
                ).show()
            } catch (e: Exception) {
                Snackbar.make(
                    requireContext(),
                    requireView(),
                    "Signing up unexpectedly failed!",
                    4000
                ).show()
            }
        }
    }

    private fun switchToRegister() {
        val intent = Intent(requireContext(), RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun signIn() {
        val intent = Intent(requireContext(), HomeScreenActivity()::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}