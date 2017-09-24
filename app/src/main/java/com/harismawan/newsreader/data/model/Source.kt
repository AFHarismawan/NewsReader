package com.harismawan.newsreader.data.model

import android.view.View
import com.google.gson.annotations.SerializedName
import com.harismawan.newsreader.R
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_source.view.*

/**
 * Created by harismawan on 9/24/17.
 */

class Source : AbstractFlexibleItem<Source.SourceViewHolder>() {

    @SerializedName("id")
    var id : String? = null

    @SerializedName("name")
    var name : String? = null

    @SerializedName("description")
    var desc : String? = null

    override fun equals(other: Any?): Boolean = false

    override fun getLayoutRes(): Int = R.layout.item_source

    override fun createViewHolder(view: View?, adapter: FlexibleAdapter<out IFlexible<*>>?): SourceViewHolder =
            SourceViewHolder(view, adapter)

    override fun bindViewHolder(adapter: FlexibleAdapter<out IFlexible<*>>?, holder: SourceViewHolder?,
                                position: Int, payloads: MutableList<Any?>?) {
        val v = holder?.itemView
        v?.name?.text = name
        v?.desc?.text = desc
    }

    class SourceViewHolder(view: View?, adapter: FlexibleAdapter<out IFlexible<*>>?) : FlexibleViewHolder(view, adapter)
}