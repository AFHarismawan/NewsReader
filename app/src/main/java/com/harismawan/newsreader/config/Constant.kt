package com.harismawan.newsreader.config

import com.harismawan.newsreader.BuildConfig

/**
 * Created by harismawan on 9/24/17.
 */

class Constant {

    companion object {
        val baseUrl = "https://newsapi.org/v1/"

        /*
        change this path "/Users/harismawan/Developer/Config/newsreader" on build.gradle
        with your text file path that contain NewsApi.org API Key just like this example

        apiKey = <your api key> (without quote)
        */
        val apiKey = BuildConfig.apiKey

        val extraId = "id"
        val extraUrl = "url"
        val extraPosition = "position"
        val extraSavedQuery = "searchText"

        val typeLoadData = 0
        val typeCheckInternet = 1
    }
}