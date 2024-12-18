@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)

package com.josephhopson.flickersearch

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.josephhopson.flickersearch.ui.home.HomeScreen
import com.josephhopson.flickersearch.ui.image.ImageDetails
import com.josephhopson.flickersearch.ui.image.ImageDetailsScreen
import com.josephhopson.flickersearch.ui.theme.FlickerSearchTheme

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun FlickerSearchApp() {
    // this navigator is used to move between list and details screens
    val navigator = rememberListDetailPaneScaffoldNavigator<ImageDetails>()

    // adds support for navigating back from the detail screen
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(
        modifier = Modifier
            .fillMaxSize(),
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    onItemClick = { imageDetails ->
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, imageDetails)
                    },
                )
            }
        },
        detailPane = {
            AnimatedPane {
                navigator.currentDestination?.content?.let { imageDetails ->
                    ImageDetailsScreen(
                        imageDetails = imageDetails,
                        onItemClick = {
                            navigator.navigateTo(ListDetailPaneScaffoldRole.List)
                        }
                    )
                }
            }
        },
    )
}

/**
 * App bar to display title and conditionally display the back navigation.
 */
@Composable
fun FlickerSearchAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {

    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun FlickerSearchAppBarPreview() {
    FlickerSearchTheme {
        FlickerSearchAppBar(
            title = "Preview ",
            canNavigateBack = true
        )
    }
}