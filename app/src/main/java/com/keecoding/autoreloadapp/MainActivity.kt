package com.keecoding.autoreloadapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.keecoding.autoreloadapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        var WEB_URL = ""
        var PERIOD = 0
        var RELOAD = 1
    }

    private var isPlay = false
    private var isFirst = true
    private var ctrPeriod = 0

    fun callback() {
        Toast.makeText(this, "Berhasil Disimpan!", Toast.LENGTH_SHORT).show()
        if (isFirst) {
            b.btnPlay.performClick()
            isFirst = false
        }
    }

    private lateinit var b: ActivityMainBinding
    val webViewList = mutableListOf<WebView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_AutoReloadApp)
        setContentView(b.root)
        initWebList()

        Toast.makeText(this, "Setting URL Terlebih Dahulu!", Toast.LENGTH_LONG).show()
        SettingsFragment().show(supportFragmentManager, "Settings")

        b.btnSettings.setOnClickListener {
            SettingsFragment().show(supportFragmentManager, "Settings")
        }

        b.btnPlay.setOnClickListener {
            if (isPlay) {
                b.btnPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                stopAll()
            } else {
                b.btnPlay.setImageResource(R.drawable.ic_baseline_stop_24)
                ctrPeriod = PERIOD
                playAll()
            }
            isPlay = !isPlay
        }

    }

    private fun stopAll() {
        setClient()
        loadAll()
    }

    private fun playAll() {
        setClientAutoReload()
        loadAll()
    }

    private fun initWebList() {
        webViewList.apply {
            add(b.mainWebView)
            add(b.webView2)
            add(b.webView3)
            add(b.webView4)
            add(b.webView5)
            add(b.webView6)
            add(b.webView7)
            add(b.webView8)
            add(b.webView9)
            add(b.webView10)
        }

        webViewList.forEach {
            it.settings.javaScriptEnabled = true
        }
    }

    private fun setClient() {
        webViewList.forEach {
            it.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }
            }
        }
    }

    private fun setClientAutoReload() {
        for (i in 0 until RELOAD) {
            webViewList[i].webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (ctrPeriod==0) {
                        Toast.makeText(this@MainActivity, "$PERIOD Period", Toast.LENGTH_SHORT).show()
                        b.btnPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                        isPlay = false
                        return
                    }
                    view?.let {
                        if (it == b.mainWebView && PERIOD != -1) {
                            Toast.makeText(this@MainActivity, "${PERIOD-ctrPeriod}-th Period", Toast.LENGTH_SHORT).show()
                            ctrPeriod--
                        }
                        Log.d("GGG", "onPageFinished: AFAFFE")
                        webViewList[i].reload()
                    }
                }
            }
        }
    }

    private fun loadAll() {
        webViewList.forEach {
            it.loadUrl(WEB_URL)
        }
    }
}