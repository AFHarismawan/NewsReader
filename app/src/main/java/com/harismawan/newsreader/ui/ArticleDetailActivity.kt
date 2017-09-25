package com.harismawan.newsreader.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebViewClient
import com.harismawan.newsreader.R
import com.harismawan.newsreader.config.Constant
import kotlinx.android.synthetic.main.activity_article_detail.*
import android.webkit.WebView

class ArticleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        webView.settings.javaScriptEnabled = true
        webView.loadUrl(intent.getStringExtra(Constant.extraUrl))
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }
}