package com.sarang.instagralleryModule.compose.component

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sarang.instagralleryModule.util.compress
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.max


@HiltViewModel
class GalleryListWithPreviewViewModel @Inject constructor() : ViewModel() {
    var uiState : GalleryListWithPreviewUIState by mutableStateOf(GalleryListWithPreviewUIState())
        private set

    suspend fun onNext(context : Context) : List<String> {
        uiState = uiState.copy(isProgress = true)
        val compressedImages = compress(file     = uiState.files,
                                       context  = context)
        uiState.copy(compressedImages = compressedImages)
        uiState = uiState.copy(isProgress = false)

        return compressedImages
    }

    fun toggleMultipleSelect() {
        uiState = uiState.copy(isMutipleSelected = !uiState.isMutipleSelected)
    }

    fun setSelectImage(selectedImage : String) {
        uiState = uiState.copy(selectedImage = selectedImage)
        if (uiState.isMutipleSelected) {
            if (!uiState.selectedList.contains(selectedImage)) {
                if (uiState.selectedList.size < uiState.maxCount)
                    uiState = uiState.copy(selectedList = uiState.selectedList.apply { add(selectedImage) })
            }
            else { uiState = uiState.copy(selectedList = uiState.selectedList.apply { remove(selectedImage) }) }
        }
    }

    fun setMaxCount(maxCount: Int) {
        uiState = uiState.copy(maxCount = maxCount)
    }

}