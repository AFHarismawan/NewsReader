package com.harismawan.newsreader.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by harismawan on 9/25/17.
 */

class ListArticle {

    @SerializedName("articles")
    var articles = ArrayList<Article>()
}