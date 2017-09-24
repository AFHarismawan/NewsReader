package com.harismawan.newsreader

/**
 * Created by harismawan on 9/24/17.
 */

import android.app.Application
import com.harismawan.newsreader.util.Util

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Util.overrideFont(applicationContext, "SERIF", "fonts/montserrat_regular.ttf");
    }
}