package com.josephhopson.flickersearch

import android.app.Application
import com.josephhopson.flickersearch.data.AppContainer
import com.josephhopson.flickersearch.data.AppDataContainer

class FlickerSearchApplication : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}