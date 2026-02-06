package com.sarang.instagralleryModule.compose

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

val tag = "__GalleryListWithPreviewScreen"
@Composable
fun GalleryListWithPreviewScreen(onNext             : (List<String>) -> Unit    = {},
                                 onClose            : () -> Unit                = {},
                                 list               : List<String>              = listOf(),
                                 onSelectFolder     : (String) -> Unit          = {},
                                 selectedFolder     : String                    = "",
                                 isExpand           : Boolean                   = false,
                                 onFolder           : () -> Unit                = {},
                                 onDismissRequest   : () -> Unit                = {},
                                 folderList         : List<String>              = listOf(),
                                 maxCount           : Int                       = 10,
                                 isPhotoPickerMode  : Boolean                   = false,
                                 onPhotoPicker      : ()->Unit                  = {}) {

    var isProgress          : Boolean               by remember { mutableStateOf(false) }
    var selectedImage       : String                by remember { mutableStateOf("") }
    val selectedList        : MutableList<String>   = remember { mutableStateListOf() }
    var isMutipleSelected   : Boolean               by remember { mutableStateOf(false) }
    val coroutine           : CoroutineScope        = rememberCoroutineScope()
    val context             : Context               = LocalContext.current

    Scaffold(topBar = {
        GalleryTitleBar(
            onNext = {
                coroutine.launch {
                    isProgress = true
                    val compressedImage = compress(file = if (isMutipleSelected) selectedList
                                                          else ArrayList<String>().apply {
                                                            if(selectedImage.isNotEmpty()) add(selectedImage)
                                                                                         },
                                                   context = context)
                    if(compressedImage.isNotEmpty()) {
                        onNext.invoke(compressedImage)
                    }else{
                        Log.w(tag ,"compressedImage is empty. next doesn't work")
                    }
                    isProgress = false
                }
            },
            onClose = onClose,
            isAvailableNext = if (isMutipleSelected) !selectedList.isEmpty() else selectedImage.isNotEmpty()
        )
    }) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(Modifier.padding(it)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(selectedImage).build(),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth().height(300.dp),
                )
                GalleryMiddleBar(folder = selectedFolder, isMutipleSelected = isMutipleSelected, onFolder = onFolder, onSelectMutiple = { isMutipleSelected = !isMutipleSelected })
                if(isPhotoPickerMode)
                    Button(modifier = Modifier.fillMaxWidth(), onClick = onPhotoPicker, shape = RoundedCornerShape(0.dp)) {
                        Text("Select Photo")
                    }
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