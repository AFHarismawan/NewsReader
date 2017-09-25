package com.harismawan.newsreader.ui

import android.content.Intent
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

class ArticleActivity : AppCompatActivity(), FloatingSearchView.OnHomeActionClickListener,
        FloatingSearchView.OnQueryChangeListener {

    private var linearLayoutManager = LinearLayoutManager(this)
    private var articles = ArrayList<Article>()
    private var adapter = FlexibleAdapter<Article>(articles)
    private var savedInstanceState = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        recyclerArticle.layoutManager = linearLayoutManager

        searchArticle.setOnHomeActionClickListener(this)
        searchArticle.setOnQueryChangeListener(this)

        checkInternetConnection()

        if (savedInstanceState != null) {
            this.savedInstanceState = savedInstanceState
            linearLayoutManager.scrollToPosition(savedInstanceState.getInt(Constant.extraPosition))
        }
    }

    private fun initAdapter() {
        adapter = FlexibleAdapter(articles)
        adapter.addListener(FlexibleAdapter.OnItemClickListener { position ->
            val change = Intent(this, ArticleDetailActivity::class.java)
            change.putExtra(Constant.extraUrl, articles[position].url)
            startActivity(change)
            false
        })
        recyclerArticle.adapter = adapter

        if (this.savedInstanceState.getString(Constant.extraSavedQuery) != null) {
            val savedQuery = savedInstanceState.getString(Constant.extraSavedQuery)
            executeSearch(savedQuery)
        }
    }

    override fun onSearchTextChanged(oldQuery: String?, newQuery: String?) {
        executeSearch(newQuery)
    }

    private fun executeSearch(query: String?) {
        if (adapter.hasNewSearchText(query)) {
            adapter.searchText = query
            adapter.filterItems()
        }
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
        val call = Util.getAPIHelper()?.getAllArticle(intent.getStringExtra(Constant.extraId), Constant.apiKey)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(Constant.extraPosition, linearLayoutManager.findFirstVisibleItemPosition())
        outState.putString(Constant.extraSavedQuery, searchArticle.query)
    }

    override fun onHomeClicked() {
        finish()
    }
}
