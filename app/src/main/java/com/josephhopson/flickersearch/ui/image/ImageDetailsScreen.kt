package com.josephhopson.flickersearch.ui.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.josephhopson.flickersearch.R
import com.josephhopson.flickersearch.ui.navigation.NavigationDestination

object ImageDetailsDestination : NavigationDestination {
    override val route = "image_details"
    override val titleRes = R.string.image_detail_title
    const val IMAGE_ID_ARG = "imageId"
    val routeWithArgs = "$route/{$IMAGE_ID_ARG}"
}

@Composable
fun ImageDetailsScreen (
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO
}