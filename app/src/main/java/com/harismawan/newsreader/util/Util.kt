package com.harismawan.newsreader.util

import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.text.format.DateUtils
import com.harismawan.slapp.data.APIHelper
import com.harismawan.slapp.data.RetrofitClient
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by harismawan on 9/24/17.
 */

class Util {

    companion object {
        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }

        fun getAPIHelper(): APIHelper? =
                RetrofitClient.client?.create(APIHelper::class.java)

        fun overrideFont(context: Context, defaultFont: String, customFont: String) {
            try {
                val customFontTypeface = Typeface.createFromAsset(context.assets, customFont)
                val defaultFontTypefaceField = Typeface::class.java.getDeclaredField(defaultFont)
                defaultFontTypefaceField.isAccessible = true
                defaultFontTypefaceField.set(null, customFontTypeface)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun getTime(str: String) : String {
            val b = StringBuilder(str)
            b.deleteCharAt(str.length - 1)

            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            df.timeZone = TimeZone.getTimeZone("UTC")
            val date = df.parse(b.toString())

            return DateUtils.getRelativeTimeSpanString(date.time, System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS).toString()
        }
    }
}