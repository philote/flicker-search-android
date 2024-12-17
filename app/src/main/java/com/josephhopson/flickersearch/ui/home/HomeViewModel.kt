package com.josephhopson.flickersearch.ui.home

import android.util.Log
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

data class HomeUiState(
    val currentState: HomeUiStates = HomeUiStates.Landing
)

sealed interface HomeUiStates {
    data class Success(val imageList: List<ImageDetails>) : HomeUiStates
    data object Error : HomeUiStates
    data object Loading : HomeUiStates
    data object Landing : HomeUiStates
}

class HomeViewModel(private val imageRepository: ImageRepository) : ViewModel() {

    // backing properties to avoid state updates from other classes
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    var userSearchTags by mutableStateOf("")
        private set

    fun updateSearchTerm(tags: String) {
        userSearchTags = tags
        getImages()
    }

    fun getImages() {
        // TODO a timer to not hammer the api for fast typing
        _uiState.value = HomeUiState(
            currentState = HomeUiStates.Loading
        )
        viewModelScope.launch {
            _uiState.value = HomeUiState(
                currentState = getSearchResultFromRepo()
            )
        }
    }

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
