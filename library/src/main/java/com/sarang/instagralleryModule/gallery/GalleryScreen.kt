package com.sarang.instagralleryModule.gallery

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mediacontentresolverlibrary.MediaContentResolver
import com.sarang.instagralleryModule.FolderListBottomSheetDialog
import com.sarang.instagralleryModule.R

@Composable
fun GalleryScreen(
    color: Long,
    onNext: (List<String>) -> Unit,
    onClose: (Void?) -> Unit
) {
    val mediaContentResolver: MediaContentResolver =
        MediaContentResolver.newInstance(LocalContext.current)

    var list by remember { mutableStateOf(mediaContentResolver.getPictureList()) }
    var selectedImage by remember { mutableStateOf("") }
    var isExpand by remember { mutableStateOf(false) }
    var selectedFolder by remember { mutableStateOf("Recent") }
    val selectedList = remember { mutableStateListOf<String>() }
    var isMutipleSelected by remember { mutableStateOf(false) }

    Box {
        Column(Modifier.background(Color(color))) {
            //titlebar
            GalleryTitleBar(
                onNext = {
                    onNext.invoke(if (isMutipleSelected) selectedList else ArrayList<String>().apply {
                        add(selectedImage)
                    })
                },
                onClose = onClose,
                isAvailableNext = selectedImage.isNotEmpty()
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(selectedImage)
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                //contentScale = ContentScale.Crop
            )
            //center of gallery menu
            GalleryMiddleBar(
                folder = selectedFolder,
                isMutipleSelected = isMutipleSelected,
                onFolder = {
                    Log.d("GalleryScreen", "!!!!!!")
                    isExpand = !isExpand
                },
                onSelectMutiple = {
                    isMutipleSelected = !isMutipleSelected
                }
            )
            GalleryGridView(list = list,
                isMutipleSelected = isMutipleSelected,
                selectedList = selectedList,
                onClickPicture = {
                    selectedImage = it

                    if (isMutipleSelected) {
                        if (!selectedList.contains(it)) {
                            selectedList.add(it)
                        } else {
                            selectedList.remove(it)
                        }
                    }
                })
        }
        if (isExpand)
            FolderListBottomSheetDialog(isExpand) {
                selectedFolder = it
                list = mediaContentResolver.getPictureList(it)
                isExpand = false
            }
    }
}

@Preview
@Composable
fun PreviewGalleryScreen() {
    val context = LocalContext.current
    GalleryScreen(color = 0xFFFFFFFF, onNext = {
        Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
    }, onClose = {

    })
}