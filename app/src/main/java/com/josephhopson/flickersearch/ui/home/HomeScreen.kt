@file:OptIn(ExperimentalMaterial3Api::class)

package com.josephhopson.flickersearch.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
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
    navigateToItemDetail: (ImageDetails) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.uiState.collectAsState()
    val scrollBehavior = enterAlwaysScrollBehavior()

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
            onItemClick = navigateToItemDetail,
            currentState = homeUiState.currentState,
            viewModel = viewModel
        )
    }
}

@Composable
fun HomeBody(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    onItemClick: (ImageDetails) -> Unit,
    currentState: HomeUiStates,
    viewModel: HomeViewModel
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            value = viewModel.userSearchTags,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { viewModel.updateSearchTerm(it) },
            label = {
                        Text(
                            stringResource(R.string.txt_field_enter_your_search)
                        )
                    },
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.getImages()
                    focusManager.clearFocus()
                }
            )
        )
        when(currentState) {
            HomeUiStates.Landing -> Landing()
            HomeUiStates.Loading -> Loading()
            HomeUiStates.Error -> Error()
            is HomeUiStates.Success -> ImageList(
                images = currentState.imageList,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun ImageList(
    images: List<ImageDetails>,
    onItemClick: (ImageDetails) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        verticalItemSpacing = dimensionResource(R.dimen.padding_extra_small),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_small)),
        content = {
            items(images) { image ->
                AsyncImage(
                    model = image.imageThumbUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable { onItemClick(image) }
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(dimensionResource(R.dimen.icon_large)),
            painter = painterResource(R.drawable.loading),
            contentDescription = stringResource(R.string.loading)
        )
        Text(
            text = stringResource(R.string.loading),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
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
            modifier = Modifier.size(dimensionResource(R.dimen.icon_large)),
            painter = painterResource(R.drawable.image_search),
            contentDescription = stringResource(R.string.enter_tags_above)
        )
        Text(
            text = stringResource(R.string.enter_tags_above),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
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
            modifier = Modifier.size(dimensionResource(R.dimen.icon_large)),
            painter = painterResource(R.drawable.error),
            contentDescription = stringResource(R.string.error)
        )
        Text(
            text = stringResource(R.string.error),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            style=MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
