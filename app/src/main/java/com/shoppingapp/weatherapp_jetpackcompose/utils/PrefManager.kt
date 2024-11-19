package com.pdftoexcel.bankstatementconverter.utils

import android.content.Context
import android.content.SharedPreferences

class PrefManager(val context: Context) {

    var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(context.packageName, 0)
    }

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).commit()
    }

    fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).commit()
    }

    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).commit()
    }

    fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, -1)!!
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)!!
    }


}