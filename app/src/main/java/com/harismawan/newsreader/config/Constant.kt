package com.harismawan.newsreader.config

import com.harismawan.newsreader.BuildConfig

/**
 * Created by harismawan on 9/24/17.
 */

class Constant {

    companion object {
        val baseUrl = BuildConfig.baseUrl
        val apiKey = BuildConfig.apiKey

        val extraId = "id"
        val typeLoadData = 0
        val typeCheckInternet = 1
    }
}