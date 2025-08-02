package com.sarang.instagralleryModule.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sarang.instagralleryModule.usecase.GetFolderListUseCase
import com.sarang.instagralleryModule.usecase.GetPictureListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


data class GalleryUiState(
    val list: List<String> = ArrayList(),       // 이미지 리스트
    val isExpand: Boolean = false,              // 폴더 리스트 다이얼로그 표시 여부
    val selectedFolder: String = "Recent",      // 선택 된 폴더명
    val folderList: List<String> = ArrayList(),  // 폴더 리스트
)

@HiltViewModel
class GalleryViewModel @Inject constructor(
    val getPictureListUseCase: GetPictureListUseCase,
    val getFolderListUseCase: GetFolderListUseCase
) :
    ViewModel() {

    var uiState by mutableStateOf(GalleryUiState())
        private set

    init {
        reload()
    }

    // 이미지 폴더 리스트 가져오기
    fun reload() {
        uiState = uiState.copy(
            list = getPictureListUseCase.invoke(),
            folderList = getFolderListUseCase.invoke()
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
            list = getPictureListUseCase.invoke(folder)
        )
    }
}