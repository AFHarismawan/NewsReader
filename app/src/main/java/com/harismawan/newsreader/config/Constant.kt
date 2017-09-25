package com.harismawan.newsreader.config

import com.harismawan.newsreader.BuildConfig

/**
 * Created by harismawan on 9/24/17.
 */

class Constant {

    companion object {
        val baseUrl = "https://newsapi.org/v1/"
        val apiKey = BuildConfig.apiKey

        val extraId = "id"
        val extraUrl = "url"
        val extraPosition = "position"

        val typeLoadData = 0
        val typeCheckInternet = 1
    }
}