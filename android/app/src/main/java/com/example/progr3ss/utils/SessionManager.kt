package com.example.progr3ss.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }

    fun saveAuthToken(token: String) {
        prefs.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    fun saveRefreshToken(token: String) {
        prefs.edit().putString(KEY_REFRESH_TOKEN, token).apply()
    }

    fun fetchAuthToken(): String? = prefs.getString(KEY_AUTH_TOKEN, null)

    fun clearAuthToken() {
        prefs.edit().remove(KEY_AUTH_TOKEN).remove(KEY_REFRESH_TOKEN).apply()
    }
}