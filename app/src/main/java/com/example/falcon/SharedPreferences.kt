package com.example.falcon

import android.content.Context
import android.content.SharedPreferences


class SharedPreferences(context: Context) {

    private val PREFS_NAME = "MyPrefsFile" // The name of your SharedPreferences file
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPrefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPrefs.getString(key, defaultValue) ?: defaultValue
    }

}
