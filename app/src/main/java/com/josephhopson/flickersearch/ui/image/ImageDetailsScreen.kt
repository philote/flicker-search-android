@file:OptIn(ExperimentalMaterial3Api::class)

package com.josephhopson.flickersearch.ui.image

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.josephhopson.flickersearch.FlickerSearchAppBar
import com.josephhopson.flickersearch.R

@Composable
fun ImageDetailsScreen (
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
        ImageDetail(
            modifier = Modifier.padding(innerPadding),
            imageDetails = imageDetails
        )
    }
}

@Composable
fun ImageDetail(
    modifier: Modifier = Modifier,
    imageDetails: ImageDetails
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        AsyncImage(
            model = imageDetails.imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(dimensionResource(R.dimen.padding_extra_small))
        )
        Row(
            modifier = Modifier.padding(0.dp, dimensionResource(R.dimen.padding_extra_small))
        ) {
            Text(
                text = "Author: ",
                fontWeight = FontWeight.Bold
            )
            Text(text = imageDetails.author)
        }
        Row(
            modifier = Modifier.padding(0.dp, dimensionResource(R.dimen.padding_extra_small))
        ) {
            Text(
                text = "Date Taken: ",
                fontWeight = FontWeight.Bold
            )
            Text(text = imageDetails.dateTaken)
        }
        Row(
            modifier = Modifier.padding(0.dp, dimensionResource(R.dimen.padding_extra_small))
        ) {
            Text(
                text = "Tags: ",
                fontWeight = FontWeight.Bold
            )
            Text(text = imageDetails.tags)
        }
    }
}