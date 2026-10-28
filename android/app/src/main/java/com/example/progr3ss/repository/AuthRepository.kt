package com.example.progr3ss.repository

import android.content.Context
import com.example.progr3ss.model.AuthRequest
import com.example.progr3ss.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class AuthRepository(context: Context) {

    private val api = RetrofitClient.getInstance(context)

    suspend fun login(email: String, password: String) =
        api.login(AuthRequest(email = email, password = password))

    suspend fun register(username: String, email: String, password: String): retrofit2.Response<okhttp3.ResponseBody> {
        val text = "text/plain".toMediaType()
        return api.register(
            username.toRequestBody(text),
            email.toRequestBody(text),
            password.toRequestBody(text)
        )
    }
}