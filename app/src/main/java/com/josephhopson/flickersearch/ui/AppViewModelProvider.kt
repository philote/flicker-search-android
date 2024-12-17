package com.josephhopson.flickersearch.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.josephhopson.flickersearch.FlickerSearchApplication
import com.josephhopson.flickersearch.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                flickerSearchApplication().container.imageRepository
            )
        }
    }
}

fun CreationExtras.flickerSearchApplication(): FlickerSearchApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FlickerSearchApplication)