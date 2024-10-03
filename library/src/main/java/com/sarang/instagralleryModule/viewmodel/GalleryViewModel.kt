package com.sarang.instagralleryModule.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mediacontentresolverlibrary.MediaContentResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject


data class GalleryUiState(
    val list: List<String> = ArrayList(),       // 이미지 리스트
    val isExpand: Boolean = false,              // 폴더 리스트 다이얼로그 표시 여부
    val selectedFolder: String = "Recent",      // 선택 된 폴더명
    val folderList: List<String> = ArrayList(),  // 폴더 리스트
)

@HiltViewModel
class GalleryViewModel @Inject constructor(val mediaContentResolver: MediaContentResolver) :
    ViewModel() {

    var uiState by mutableStateOf(GalleryUiState())
        private set

    init {
        reLoad()
    }

    // 이미지 폴더 리스트 가져오기
    fun reLoad() {
        Log.d("_GalleryViewModel", "reLoad")
        uiState = uiState.copy(
            list = mediaContentResolver.getPictureList(),
            folderList = mediaContentResolver.getFolderList()
        )
    }

    // 폴더 리스트 다이얼로그 닫기
    fun closeFoldersDialog() {
        uiState = uiState.copy(isExpand = false)
    }

    // 폴더 리스트 다이얼로그 열기
    fun openFoldersDialog() {
        uiState = uiState.copy(isExpand = true)
    }

    // 폴더 갱신하기
    fun updateFolder(folder: String) {
        uiState = uiState.copy(
            selectedFolder = folder,
            list = mediaContentResolver.getPictureList(folder)
        )
    }
}