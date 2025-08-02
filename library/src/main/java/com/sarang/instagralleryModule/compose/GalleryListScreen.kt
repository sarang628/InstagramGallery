package com.sarang.instagralleryModule.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.sarang.instagralleryModule.compose.component.FolderListBottomSheetDialog
import com.sarang.instagralleryModule.compose.component.GalleryGridView
import com.sarang.instagralleryModule.compose.component.GalleryMiddleBar1

/**
 * @param list 이미지 리스트
 * @param onSelectFolder 폴더 선택 클릭
 * @param selectedFolder 선택 된 폴더명
 * @param isExpand 시트 확장 여부
 * @param onFolder 폴더 선택
 * @param onDismissRequest 다이얼로그 닫혔을 때
 * @param maxCount 최대 선택 갯수
 * @param onSelectedList 선택한 파일
 */
@Composable
internal fun GalleryListScreen(list: List<String>, onSelectFolder: (String) -> Unit, selectedFolder: String, isExpand: Boolean, onFolder: () -> Unit, onDismissRequest: () -> Unit, folderList: List<String>, maxCount: Int = 10, onSelectedList: (List<String>) -> Unit = {}, ) {
    var selectedImage by remember { mutableStateOf("") }
    val selectedList = remember { mutableStateListOf<String>() }
    var isMutipleSelected by remember { mutableStateOf(true) }

    Box {
        Column {
            GalleryMiddleBar1(folder = selectedFolder, onFolder = onFolder)
            GalleryGridView(list = list, isMutipleSelected = isMutipleSelected, selectedList = selectedList,
                onClickPicture = {
                    selectedImage = it
                    if (isMutipleSelected) {
                        if (!selectedList.contains(it)) { if (selectedList.size < maxCount) selectedList.add(it) }
                        else { selectedList.remove(it) }
                    }
                    onSelectedList.invoke(selectedList)
                })
        }
        FolderListBottomSheetDialog(
            isExpand,
            onSelect = onSelectFolder,
            onDismissRequest = onDismissRequest,
            list = folderList
        )
    }
}

@Preview
@Composable
fun PreviewGalleryListScreen() {
    GalleryListScreen(
        list = ArrayList<String>().apply { add("");add("");add("");add("");add("");add("");add("");add("");add("");add("");add("") },
        isExpand = false,
        onFolder = {},
        onSelectFolder = {},
        selectedFolder = "Selected Folder",
        onDismissRequest = {},
        folderList = ArrayList()
    )
}