package com.keecoding.autoreloadapp

import android.app.Application

class App: Application() {
    override fun onCreate() {
        SharedPref.init(this)
        super.onCreate()
    }
}