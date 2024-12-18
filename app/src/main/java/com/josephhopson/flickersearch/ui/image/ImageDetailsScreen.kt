@file:OptIn(ExperimentalMaterial3Api::class)

package com.josephhopson.flickersearch.ui.image

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.josephhopson.flickersearch.FlickerSearchAppBar
import com.josephhopson.flickersearch.R

@Composable
fun ImageDetailsScreen (
    modifier: Modifier = Modifier,
    imageDetails: ImageDetails,
    onItemClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            FlickerSearchAppBar(
                title = stringResource(R.string.image_detail_title),
                scrollBehavior = enterAlwaysScrollBehavior(),
                canNavigateBack = true,
                navigateUp = onItemClick
            )
        }
    ) { innerPadding ->
        Text(
            modifier = Modifier.padding(innerPadding),
            text = "Hello World!"
        )
    }
}