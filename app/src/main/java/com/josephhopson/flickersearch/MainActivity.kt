package com.josephhopson.flickersearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.josephhopson.flickersearch.ui.theme.FlickerSearchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlickerSearchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    FlickerSearchApp()
                }
            }
        }
    }
}