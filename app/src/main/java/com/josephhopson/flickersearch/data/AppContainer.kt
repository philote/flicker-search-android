package com.josephhopson.flickersearch.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val imageRepository: ImageRepository
}

/**
 * [AppContainer] implementation that provides instance of [ImageRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {

    /**
     * Implementation for [ImageRepository]
     */
    override val imageRepository: ImageRepository by lazy {
        NetworkImageDataRepository()
    }

}
