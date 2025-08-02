package com.sarang.instagralleryModule.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sarang.instagralleryModule.compose.component.FolderListBottomSheetDialog
import com.sarang.instagralleryModule.compose.component.GalleryGridView
import com.sarang.instagralleryModule.compose.component.GalleryMiddleBar
import com.sarang.instagralleryModule.compose.component.GalleryTitleBar
import com.sarang.instagralleryModule.util.compress
import kotlinx.coroutines.launch

/**
 * @param onNext 다음 클릭
 * @param onClose 다음 클릭
 * @param list 이미지 리스트
 * @param onSelectFolder 폴더 선택 클릭
 * @param selectedFolder 선택 된 폴더명
 * @param isExpand 폴더 리스트 다이얼로그 표시 여부
 * @param onFolder 폴더 리스트 다이얼로그 클릭
 * @param onDismissRequest 폴더 리스트 다이얼로그 닫기 이벤트
 * @param folderList 폴더 리스트
 */
@Composable
fun GalleryListWithPreviewScreen(onNext: (List<String>) -> Unit = {}, onClose: () -> Unit = {}, list: List<String> = listOf<String>(), onSelectFolder: (String) -> Unit = {}, selectedFolder: String = "", isExpand: Boolean = false, onFolder: () -> Unit = {}, onDismissRequest: () -> Unit = {}, folderList: List<String> = listOf<String>(), maxCount : Int = 10) {
    var isProgress by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableStateOf("") }
    val selectedList = remember { mutableStateListOf<String>() }
    var isMutipleSelected by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    Box {
        Column {
            GalleryTitleBar(
                onNext = {
                    coroutine.launch {
                        isProgress = true
                        val compressedImage = compress(if (isMutipleSelected) selectedList else ArrayList<String>().apply { add(selectedImage) }, context = context)
                        onNext.invoke(compressedImage)
                        isProgress = false
                    }
                },
                onClose = onClose,
                isAvailableNext = if (isMutipleSelected) !selectedList.isEmpty() else selectedImage.isNotEmpty()
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(selectedImage).build(),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth().height(300.dp),
            )
            GalleryMiddleBar(folder = selectedFolder, isMutipleSelected = isMutipleSelected, onFolder = onFolder, onSelectMutiple = { isMutipleSelected = !isMutipleSelected })
            GalleryGridView(list = list, isMutipleSelected = isMutipleSelected, selectedList = selectedList,
                onClickPicture = {
                    selectedImage = it
                    if (isMutipleSelected) {
                        if (!selectedList.contains(it)) { if (selectedList.size < maxCount) selectedList.add(it) }
                        else { selectedList.remove(it) }
                    }
                })
        }
        FolderListBottomSheetDialog(isExpand = isExpand, onSelect = onSelectFolder, onDismissRequest = onDismissRequest, list = folderList)

        if (isProgress)
            Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Text(text = "compressing..")
            }
    }
}

@Preview
@Composable
fun PreviewGalleryScreen() {
    GalleryListWithPreviewScreen(onNext = {}, onClose = {}, list = ArrayList<String>().apply { add("");add("");add("");add("");add("");add("");add("");add("");add("");add("");add(""); },
        isExpand = false,
        onFolder = {},
        onSelectFolder = {},
        selectedFolder = "Selected Folder",
        onDismissRequest = {},
        folderList = ArrayList()
    )
}