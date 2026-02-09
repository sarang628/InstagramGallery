package com.sarang.instagralleryModule.compose

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.instagralleryModule.compose.component.FolderListBottomSheetDialog
import com.sarang.instagralleryModule.compose.component.GalleryGridView
import com.sarang.instagralleryModule.compose.component.GalleryListWithPreviewUIState
import com.sarang.instagralleryModule.compose.component.GalleryListWithPreviewViewModel
import com.sarang.instagralleryModule.compose.component.GalleryMiddleBar
import com.sarang.instagralleryModule.compose.component.GalleryTitleBar
import com.sarang.instagralleryModule.compose.component.ImageLoadData
import com.sarang.instagralleryModule.compose.component.ImageLoadType
import com.sarang.instagralleryModule.compose.component.LocalImageLoader
import com.sarang.instagralleryModule.compose.component.LocalVideoLoader
import com.sarang.instagralleryModule.compose.component.VideoLoadData
import com.sarang.instagralleryModule.compose.component.VideoLoadType
import kotlinx.coroutines.launch

private val GalleryListWithPreviewUIState.isAvailableNext: Boolean get() {
    return if (isMutipleSelected) !selectedList.isEmpty()
           else selectedImage.isNotEmpty()
}
val tag = "__GalleryListWithPreviewScreen"
@Composable
fun GalleryListWithPreviewScreen(viewModel          : GalleryListWithPreviewViewModel   = hiltViewModel(),
                                 onNext             : (List<String>) -> Unit            = {},
                                 onClose            : () -> Unit                        = {},
                                 list               : List<String>                      = listOf(),
                                 onSelectFolder     : (String) -> Unit                  = {},
                                 selectedFolder     : String                            = "",
                                 isExpand           : Boolean                           = false,
                                 onFolder           : () -> Unit                        = {},
                                 onDismissRequest   : () -> Unit                        = {},
                                 folderList         : List<String>                      = listOf(),
                                 maxCount           : Int                               = 10,
                                 isPhotoPickerMode  : Boolean                           = false,
                                 onPhotoPicker      : ()->Unit                          = {},
                                 imageLoader        : ImageLoadType                     = {},
                                 videoLoader        : VideoLoadType                     = {}) {
    val uiState : GalleryListWithPreviewUIState = viewModel.uiState
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(maxCount) {
        viewModel.setMaxCount(maxCount)
    }

    Scaffold(topBar = {
        GalleryTitleBar(
            onNext          = { scope.launch {
                onNext(viewModel.onNext(context))
            } },
            onClose         = onClose,
            isAvailableNext = uiState.isAvailableNext
        )
    }) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(Modifier.padding(it)) {
                CompositionLocalProvider(LocalImageLoader provides imageLoader,
                                                   LocalVideoLoader provides videoLoader) {
                    ImageVideoLoader(modifier = Modifier.fillMaxWidth()
                                                        .height(300.dp),
                                     uri = uiState.selectedImage,
                                     isVideo = isVideo(context, uiState.selectedImage.toUri()))
                    GalleryMiddleBar(
                        folder                 = selectedFolder,
                        isMutipleSelected      = uiState.isMutipleSelected,
                        onFolder               = onFolder,
                        onSelectMutiple        = viewModel::toggleMultipleSelect)
                    if(isPhotoPickerMode)
                        Button(
                            modifier     = Modifier.fillMaxWidth(),
                            onClick      = onPhotoPicker,
                            shape        = RoundedCornerShape(0.dp)) {
                            Text("Select Photo")
                        }
                    GalleryGridView(
                        list                = list,
                        isMultipleSelected  = uiState.isMutipleSelected,
                        selectedList        = uiState.selectedList,
                        onClickPicture      = { viewModel.setSelectImage(it) })
                }
            }
            FolderListBottomSheetDialog(isExpand = isExpand, onSelect = onSelectFolder, onDismissRequest = onDismissRequest, list = folderList)
            if (uiState.isProgress)
                Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Text(text = "compressing..")
                }
        }
    }
}

@Composable
fun ImageVideoLoader(
    uri         : String    = "",
    modifier    : Modifier  = Modifier,
    isVideo     : Boolean   = false,
    onClick     : ()->Unit  = {},
    showVideoIcon : Boolean = false
) {
    var isActive : Boolean by remember { mutableStateOf(false) }

    LaunchedEffect(uri) {
        //url 변경 시 LocalVideoLoader의 active 값을 갱신해줘야 플레이됨.
        isActive = false
        isActive = true
    }

    Box(modifier = modifier.clickable(true, onClick = onClick)){
        if (isVideo) {
            Box() {
                LocalVideoLoader.current.invoke(
                    VideoLoadData(url = uri,
                        isActive = isActive)
                )
                // 🔥 터치 전용 오버레이
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable(onClick = onClick)
                )
                if(showVideoIcon)
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
                            .padding(3.dp)
                            .align(Alignment.TopEnd)
                    )
            }
        } else {
            LocalImageLoader.current.invoke(
                ImageLoadData(url = uri,
                    contentScale = ContentScale.Crop)
            )
        }
    }
}

fun isVideo(context: Context, uri: Uri): Boolean {
    val mimeType = context.contentResolver.getType(uri)
    return mimeType?.startsWith("video") == true
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
        folderList = listOf("a","b","c","d","e","f","g")
    )
}

@Preview
@Composable
fun PreviewImageVideoLoader(){
    ImageVideoLoader(
        isVideo = true
    )
}