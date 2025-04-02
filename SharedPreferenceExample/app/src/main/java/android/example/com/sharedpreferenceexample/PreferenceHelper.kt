package com.example.sharedprefdemo.helper

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("TH4", Context.MODE_PRIVATE)

    fun saveUser(username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("USERNAME", username)
        editor.putString("PASSWORD", password)
        editor.apply()
    }

    fun getUser(): Pair<String?, String?> {
        val username = sharedPreferences.getString("USERNAME", null)
        val password = sharedPreferences.getString("PASSWORD", null)
        return Pair(username, password)
    }

    fun deleteUser() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
