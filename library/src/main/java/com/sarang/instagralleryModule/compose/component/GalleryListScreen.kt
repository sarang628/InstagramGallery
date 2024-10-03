package com.sarang.instagralleryModule.compose.component

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

@Composable
internal fun GalleryListScreen(
    onNext: (List<String>) -> Unit,     // 다음 클릭
    onClose: () -> Unit,                // 닫기 클릭
    list: List<String>,                 // 이미지 리스트
    onSelectFolder: (String) -> Unit,   // 폴더 선택 클릭
    selectedFolder: String,             // 선택 된 폴더명
    isExpand: Boolean,                  // 폴더 리스트 다이얼로그 표시 여부
    onFoler: () -> Unit,                // 폴더 리스트 다이얼로그 클릭
    onDismissRequest: () -> Unit,       // 폴더 리스트 다이얼로그 닫기 이벤트
    folderList: List<String>,            // 폴더 리스트
    maxCount: Int = 10,
) {
    var isProgress by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableStateOf("") }
    val selectedList = remember { mutableStateListOf<String>() }
    var isMutipleSelected by remember { mutableStateOf(true) }

    Box {
        Column {
            //center of gallery menu
            GalleryMiddleBar1(
                folder = selectedFolder,
                onFolder = onFoler
            )
            GalleryGridView(list = list,
                isMutipleSelected = isMutipleSelected,
                selectedList = selectedList,
                onClickPicture = {
                    selectedImage = it

                    if (isMutipleSelected) {
                        if (!selectedList.contains(it)) {
                            if (selectedList.size < maxCount)
                                selectedList.add(it)
                        } else {
                            selectedList.remove(it)
                        }
                    }
                })
        }
        FolderListBottomSheetDialog(
            isExpand,
            onSelect = onSelectFolder,
            onDismissRequest = onDismissRequest,
            list = folderList
        )

        if (isProgress)
            Column(
                Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(text = "compressing..")
            }

    }
}

@Preview
@Composable
fun PreviewGalleryListScreen() {
    GalleryListScreen(onNext = {}, onClose = {}, list = ArrayList<String>().apply {
        add("")
        add("")
        add("")
        add("")
        add("")
        add("")
        add("")
        add("")
        add("")
        add("")
        add("")
    },
        isExpand = false,
        onFoler = {},
        onSelectFolder = {},
        selectedFolder = "Selected Folder",
        onDismissRequest = {},
        folderList = ArrayList()
    )
}