package com.sergiotorres.grazer.data.local

import android.content.SharedPreferences
import javax.inject.Inject

// TODO: we should use an encrypted persistence layer to store the token, not just raw SharedPreferences
class AuthLocalDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun getAuthToken(): String? {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    fun setAuthToken(token: String) {
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    companion object {
        private const val KEY_AUTH_TOKEN = "authToken"
    }
}
