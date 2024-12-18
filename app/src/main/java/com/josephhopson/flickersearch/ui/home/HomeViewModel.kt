package com.josephhopson.flickersearch.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josephhopson.flickersearch.data.ImageApiResult
import com.josephhopson.flickersearch.data.ImageRepository
import com.josephhopson.flickersearch.ui.image.ImageDetails
import com.josephhopson.flickersearch.ui.image.toImageDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * the current UI state for the home screen
 * @property currentState initialized as Landing
 */
data class HomeUiState(
    val currentState: HomeUiStates = HomeUiStates.Landing
)

/**
 * UI states for the HomeScreen
 * 4 possible states for the homeScreen UI: Success with data, Error, Loading, Landing.
 * Landing is what is used to represent a fresh UI State, like when the app is started.
 */
sealed interface HomeUiStates {
    data class Success(val imageList: List<ImageDetails>) : HomeUiStates
    data object Error : HomeUiStates
    data object Loading : HomeUiStates
    data object Landing : HomeUiStates
}

/**
 * ViewModel for the HomeScreen
 *
 * @param [imageRepository] the DI Injected image repository used to get the images
 */
class HomeViewModel(private val imageRepository: ImageRepository) : ViewModel() {

    // backing properties to avoid state updates from other classes
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /**
     * The mutable State that stores the status of the most recent user input for the search tags
     */
    var userSearchTags by mutableStateOf("")
        private set

    /**
     * Update the search term and get the images
     */
    fun updateSearchTerm(tags: String) {
        userSearchTags = tags
        if (userSearchTags.isEmpty()) {
            clear()
        }
        if (userSearchTags.length > 2) {
            getImages()
        }
    }

    /**
     * Clear the UI state
     */
    private fun clear() {
        _uiState.value = HomeUiState(
            currentState = HomeUiStates.Landing
        )
    }

    /**
     * Set the initial UI state and Get the images from the repo
     */
    private fun getImages() {
        _uiState.value = HomeUiState(
            currentState = HomeUiStates.Loading
        )
        viewModelScope.launch {
            _uiState.value = HomeUiState(
                currentState = getSearchResultFromRepo()
            )
        }
    }

    /**
     * Get search result from repo
     *
     * @return the search result wrapped in a [HomeUiStates]
     */
    private suspend fun getSearchResultFromRepo(): HomeUiStates {
        return when(
            val imageApiResult = imageRepository.getImageSearchResults(userSearchTags)
        ) {
            is ImageApiResult.Success -> {
                // Transform the repo image data structures to the UI image data structures
                val imageDetailsList = imageApiResult.images.map { it.toImageDetails() }
                HomeUiStates.Success(imageDetailsList)
            }
            ImageApiResult.Error -> HomeUiStates.Error
        }
    }
}
