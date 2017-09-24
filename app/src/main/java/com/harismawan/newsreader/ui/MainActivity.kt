package com.harismawan.newsreader.ui

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.harismawan.newsreader.R
import com.harismawan.newsreader.data.model.ListSource
import com.harismawan.newsreader.data.model.Source
import com.harismawan.newsreader.util.Util
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val typeLoadData = 0
    private val typeCheckInternet = 1

    private var sources = ArrayList<Source>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerSource.layoutManager = linearLayoutManager

        checkInternetConnection()
    }

    private fun initAdapter() {
        val adapter = FlexibleAdapter<Source>(sources)
        adapter.addListener(FlexibleAdapter.OnItemClickListener { position ->
//            val change = Intent(this, VideoActivity::class.java)
//            change.putExtra(Constant.extraLink, words[position].link)
//            startActivity(change)
            false
        })
        recyclerSource.adapter = adapter
    }

    private fun checkInternetConnection() {
        if (!Util.isConnected(this)) {
            progressBar.visibility = View.GONE
            showSnackbar(R.string.no_internet, R.string.retry, typeCheckInternet)
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
                    showSnackbar(R.string.failed_get_data, R.string.retry, typeLoadData)
                }
            }

            override fun onFailure(call: Call<ListSource>, t: Throwable) {
                t.printStackTrace()
                progressBar.visibility = View.GONE
                showSnackbar(R.string.failed_connect_server, R.string.retry, typeLoadData)
            }
        })
    }
}
