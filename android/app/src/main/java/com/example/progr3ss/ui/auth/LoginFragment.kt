package com.example.progr3ss.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.progr3ss.R
import com.example.progr3ss.databinding.FragmentLoginBinding
import com.example.progr3ss.utils.SessionManager

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            Log.d("LoginFragment", "Login attempt, email: $email")

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Email and Password are required", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(email, password)
            }
        }

        binding.btnGoRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            binding.btnLogin.isEnabled = !loading
        }

        viewModel.authResult.observe(viewLifecycleOwner) { result ->
            Log.d("LoginFragment", "authResult: $result")
            result.onSuccess { authResponse ->
                val name = authResponse.user?.profile?.username ?: authResponse.user?.email ?: ""
                Toast.makeText(requireContext(), "Welcome $name", Toast.LENGTH_LONG).show()

                val session = SessionManager(requireContext().applicationContext)
                session.saveAuthToken(authResponse.tokens.accessToken)
                session.saveRefreshToken(authResponse.tokens.refreshToken)

                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }.onFailure { error ->
                Toast.makeText(requireContext(), "Login failed: ${error.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}