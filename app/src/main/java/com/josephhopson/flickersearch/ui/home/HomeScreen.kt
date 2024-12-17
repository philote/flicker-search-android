@file:OptIn(ExperimentalMaterial3Api::class)

package com.josephhopson.flickersearch.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.josephhopson.flickersearch.FlickerSearchAppBar
import com.josephhopson.flickersearch.R
import com.josephhopson.flickersearch.ui.AppViewModelProvider
import com.josephhopson.flickersearch.ui.image.ImageDetails
import com.josephhopson.flickersearch.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    navigateToItemDetail: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.uiState.collectAsState()
    val scrollBehavior = enterAlwaysScrollBehavior()
    viewModel.updateSearchTerm("cat")

    Scaffold(
        modifier = modifier,
        topBar = {
            FlickerSearchAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        HomeBody(
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
            currentState = homeUiState.currentState
        )
    }
}

@Composable
fun HomeBody(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    currentState: HomeUiStates
) {
    Text(
        text = "Hello World!"
    )
    when(currentState) {
        HomeUiStates.Landing -> Landing()
        HomeUiStates.Loading -> Loading()
        HomeUiStates.Error -> Error()
        is HomeUiStates.Success -> ImageList(
            images = currentState.imageList,
        )
    }
}

@Composable
fun ImageList(
    images: List<ImageDetails>,
//    onItemClick: (ImageDetails) -> Unit,
) {
    LazyColumn {
        items(images) {image ->
            ImageCard(
                image = image,
            )
        }
    }
}

@Composable
fun ImageCard(
    image: ImageDetails,
    modifier: Modifier = Modifier,
//    onItemClick: (Forecast) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
//            .clickable { onItemClick(forecast) },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row {
                Text(
                    text = "Description: ",
                    fontWeight = FontWeight.Bold
                )
                Text(text = image.description)
            }
            Row {
                Text(
                    text = "URL: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = image.imageUrl
                )
            }
            Row {
                Text(
                    text = "Thumb URL: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = image.imageThumbUrl
                )
            }
            Text(
                text = image.tags
            )
        }
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(R.drawable.loading),
            contentDescription = stringResource(R.string.loading)
        )
        Text(
            text = stringResource(R.string.loading),
            modifier = Modifier.padding(16.dp),
            style= MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun Landing() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(R.drawable.image_search),
            contentDescription = stringResource(R.string.enter_tags_above)
        )
        Text(
            text = stringResource(R.string.enter_tags_above),
            modifier = Modifier
                .padding(16.dp),
            style=MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun Error() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(R.drawable.error),
            contentDescription = stringResource(R.string.error)
        )
        Text(
            text = stringResource(R.string.error),
            modifier = Modifier.padding(16.dp),
            style=MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
