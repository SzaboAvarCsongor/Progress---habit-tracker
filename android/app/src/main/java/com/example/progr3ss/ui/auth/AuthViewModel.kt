package com.example.progr3ss.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.progr3ss.model.AuthResponse
import com.example.progr3ss.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository(application)

    private val _authResult = MutableLiveData<Result<AuthResponse>>()
    val authResult: LiveData<Result<AuthResponse>> = _authResult

    private val _registerResult = MutableLiveData<Result<Unit>>()
    val registerResult: LiveData<Result<Unit>> = _registerResult

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.login(email, password)
                handleLoginResponse(response)
            } catch (e: Exception) {
                _authResult.value = Result.failure(e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.register(username, email, password)
                if (response.isSuccessful) {
                    _registerResult.value = Result.success(Unit)
                } else {
                    _registerResult.value = Result.failure(
                        Exception("Registration failed: ${response.code()} ${response.errorBody()?.string().orEmpty()}")
                    )
                }
            } catch (e: Exception) {
                _registerResult.value = Result.failure(e)
            } finally {
                _loading.value = false
            }
        }
    }

    private fun handleLoginResponse(response: Response<AuthResponse>) {
        val body = response.body()
        if (response.isSuccessful && body != null) {
            _authResult.value = Result.success(body)
        } else {
            _authResult.value = Result.failure(Exception("Auth failed: ${response.code()}"))
        }
    }
}