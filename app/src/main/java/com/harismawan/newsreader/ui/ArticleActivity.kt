package com.harismawan.newsreader.ui

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.arlib.floatingsearchview.FloatingSearchView
import com.harismawan.newsreader.R
import com.harismawan.newsreader.config.Constant
import com.harismawan.newsreader.data.model.Article
import com.harismawan.newsreader.data.model.ListArticle
import com.harismawan.newsreader.util.Util
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.activity_article.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleActivity : AppCompatActivity(), FloatingSearchView.OnHomeActionClickListener {

    private var id = ""
    private var articles = ArrayList<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        id = intent.getStringExtra(Constant.extraId)
        searchArticle.setOnHomeActionClickListener(this)

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerArticle.layoutManager = linearLayoutManager

        checkInternetConnection()
    }

    private fun initAdapter() {
        val adapter = FlexibleAdapter<Article>(articles)
        adapter.addListener(FlexibleAdapter.OnItemClickListener { position ->

            false
        })
        recyclerArticle.adapter = adapter
    }

    private fun checkInternetConnection() {
        if (!Util.isConnected(this)) {
            progressBar.visibility = View.GONE
            showSnackbar(R.string.no_internet, R.string.retry, Constant.typeCheckInternet)
        } else {
            loadData()
        }
    }

    private fun showSnackbar(message: Int, action: Int, type: Int) {
        val snackbar = Snackbar
                .make(root, getString(message), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(action)) {
                    progressBar.visibility = View.VISIBLE
                    if (type == 0) loadData()
                    else if (type == 1) checkInternetConnection()
                }

        snackbar.setActionTextColor(Color.RED)
        val sbView = snackbar.view
        val textView = sbView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        snackbar.show()
    }

    private fun loadData() {
        val call = Util.getAPIHelper()?.getAllArticle(id, Constant.apiKey)
        call?.enqueue(object : Callback<ListArticle> {
            override fun onResponse(call: Call<ListArticle>, response: Response<ListArticle>) {
                if (response.isSuccessful) {
                    articles.clear()
                    articles.addAll(response.body()!!.articles)
                    initAdapter()
                    progressBar.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE
                    showSnackbar(R.string.failed_get_data, R.string.retry, Constant.typeLoadData)
                }
            }

            override fun onFailure(call: Call<ListArticle>, t: Throwable) {
                t.printStackTrace()
                progressBar.visibility = View.GONE
                showSnackbar(R.string.failed_connect_server, R.string.retry, Constant.typeLoadData)
            }
        })
    }

    override fun onHomeClicked() {
        finish()
    }
}
