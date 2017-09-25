package com.harismawan.newsreader.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.harismawan.newsreader.R
import com.harismawan.newsreader.config.Constant
import kotlinx.android.synthetic.main.activity_article_detail.*

class ArticleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        webView.settings.javaScriptEnabled = true
        webView.loadUrl(intent.getStringExtra(Constant.extraUrl))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }
}
