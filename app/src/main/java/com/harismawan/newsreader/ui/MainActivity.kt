package com.harismawan.newsreader.ui

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.arlib.floatingsearchview.FloatingSearchView
import com.harismawan.newsreader.R
import com.harismawan.newsreader.config.Constant
import com.harismawan.newsreader.data.model.ListSource
import com.harismawan.newsreader.data.model.Source
import com.harismawan.newsreader.util.Util
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity(), FloatingSearchView.OnQueryChangeListener {

    private var linearLayoutManager = LinearLayoutManager(this)
    private var sources = ArrayList<Source>()
    private var adapter = FlexibleAdapter<Source>(sources)
    private var savedInstanceState = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerSource.layoutManager = linearLayoutManager

        checkInternetConnection()

        searchSource.setOnQueryChangeListener(this)

        if (savedInstanceState != null) {
            this.savedInstanceState = savedInstanceState
            linearLayoutManager.scrollToPosition(savedInstanceState.getInt(Constant.extraPosition))
        }
    }

    private fun initAdapter() {
        adapter = FlexibleAdapter(sources)
        adapter.addListener(FlexibleAdapter.OnItemClickListener { position ->
            val change = Intent(this, ArticleActivity::class.java)
            change.putExtra(Constant.extraId, sources[position].id)
            startActivity(change)
            false
        })
        recyclerSource.adapter = adapter

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
        val call = Util.getAPIHelper()?.getAllSource()
        call?.enqueue(object : Callback<ListSource> {
            override fun onResponse(call: Call<ListSource>, response: Response<ListSource>) {
                if (response.isSuccessful) {
                    sources.clear()
                    sources.addAll(response.body()!!.sources)
                    initAdapter()
                    progressBar.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE
                    showSnackbar(R.string.failed_get_data, R.string.retry, Constant.typeLoadData)
                }
            }

            override fun onFailure(call: Call<ListSource>, t: Throwable) {
                t.printStackTrace()
                progressBar.visibility = View.GONE
                showSnackbar(R.string.failed_connect_server, R.string.retry, Constant.typeLoadData)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constant.extraSavedQuery, searchSource.query)
        outState.putInt(Constant.extraPosition, linearLayoutManager.findFirstVisibleItemPosition())
    }
}
