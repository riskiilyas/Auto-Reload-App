package com.keecoding.autoreloadapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object SharedPref {
    private var sharedPreferences: SharedPreferences? = null

    fun init(app: Application) {
        sharedPreferences = app.getSharedPreferences("RELOAD_APP", Context.MODE_PRIVATE)
    }

    var url: String
        get() = sharedPreferences!!.getString("URL", "")!!
        set(value) {
            sharedPreferences!!.edit().putString("URL", value).apply()
        }

    var reload: Int
        get() = sharedPreferences!!.getInt("RELOAD", 1)
        set(value) {
            sharedPreferences!!.edit().putInt("RELOAD", value).apply()
        }

    var periose: Int
        get() = sharedPreferences!!.getInt("PERIODE", 1)
        set(value) {
            sharedPreferences!!.edit().putInt("PERIODE", value).apply()
        }
}