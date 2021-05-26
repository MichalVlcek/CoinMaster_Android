package com.example.coinapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.coinapp.HomeScreenActivity
import com.example.coinapp.databinding.RegisterFragmentBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    private var _binding: RegisterFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        viewModel.registeredUser.observe(
            viewLifecycleOwner,
            {
                signIn()
            }
        )

        binding.signUpButton.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val premium = binding.userTypeCheckbox.isChecked

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
                viewModel.registerUser(email, password, premium)
            } catch (e: UserExistsException) {
                Snackbar.make(
                    requireContext(),
                    requireView(),
                    "User with this email already exists!",
                    4000
                ).show()
            } catch (e: Exception) {
                Snackbar.make(
                    requireContext(),
                    requireView(),
                    "Registration unexpectedly failed!",
                    4000
                ).show()
            }
        }
    }

    private fun signIn() {
        //TODO pridat user ID
        val intent = Intent(requireContext(), HomeScreenActivity()::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}