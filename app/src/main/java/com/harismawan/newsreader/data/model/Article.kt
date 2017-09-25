package com.harismawan.newsreader.data.model

import android.view.View
import com.google.gson.annotations.SerializedName
import com.harismawan.newsreader.R
import com.harismawan.newsreader.util.Util
import com.squareup.picasso.Picasso
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_article.view.*

/**
 * Created by harismawan on 9/24/17.
 */

class Article : AbstractFlexibleItem<Article.ArticleViewHolder>() {

    @SerializedName("title")
    var title : String? = null

    @SerializedName("author")
    var author : String? = null

    @SerializedName("publishedAt")
    var time : String? = null

    @SerializedName("url")
    var url : String? = null

    @SerializedName("urlToImage")
    var image : String? = null

    override fun equals(other: Any?): Boolean = false

    override fun getLayoutRes(): Int = R.layout.item_article

    override fun createViewHolder(view: View?, adapter: FlexibleAdapter<out IFlexible<*>>?): Article.ArticleViewHolder =
            Article.ArticleViewHolder(view, adapter)

    override fun bindViewHolder(adapter: FlexibleAdapter<out IFlexible<*>>?, holder: Article.ArticleViewHolder?,
                                position: Int, payloads: MutableList<Any?>?) {
        val v = holder?.itemView
        Picasso.with(v?.context).load(image).into(v?.image)
        v?.title?.text = title

        if (author.equals(null)) author = ""
        else author = "| $author"

        val t = Util.getTime(time!!)
        val sub = "$t $author"
        v?.subtitle?.text = sub
    }

    class ArticleViewHolder(view: View?, adapter: FlexibleAdapter<out IFlexible<*>>?) : FlexibleViewHolder(view, adapter)
}