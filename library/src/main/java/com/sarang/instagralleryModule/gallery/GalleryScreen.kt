package com.sarang.instagralleryModule.gallery

import android.Manifest
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mediacontentresolverlibrary.MediaContentResolver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.sarang.instagralleryModule.FolderListBottomSheetDialog
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import java.io.File

@Composable
private fun _GalleryScreen(
    onNext: (List<String>) -> Unit,
    onClose: () -> Unit
) {
    val mediaContentResolver: MediaContentResolver =
        MediaContentResolver.newInstance(LocalContext.current)
    var isProgress by remember { mutableStateOf(false) }
    var list by remember { mutableStateOf(mediaContentResolver.getPictureList()) }
    var selectedImage by remember { mutableStateOf("") }
    var isExpand by remember { mutableStateOf(false) }
    var selectedFolder by remember { mutableStateOf("Recent") }
    val selectedList = remember { mutableStateListOf<String>() }
    var isMutipleSelected by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    Box {
        Column {
            //titlebar
            GalleryTitleBar(
                onNext = {
                    coroutine.launch {
                        isProgress = true
                        val compressedImage =
                            compress(if (isMutipleSelected) selectedList else ArrayList<String>().apply {
                                add(selectedImage)
                            }, context = context)
                        onNext.invoke(compressedImage)
                        isProgress = false
                    }
                },
                onClose = onClose,
                isAvailableNext = if (isMutipleSelected) !selectedList.isEmpty() else selectedImage.isNotEmpty()
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
        FolderListBottomSheetDialog(isExpand,
            onSelect = {
                selectedFolder = it
                list = mediaContentResolver.getPictureList(it)
                isExpand = false
            },
            onDismissRequest = {
                isExpand = false
            }
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
fun PreviewGalleryScreen() {
    val context = LocalContext.current
    GalleryScreen(onNext = {
        Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
    }, onClose = {

    })
}

suspend fun compress(file: List<String>, context: Context): ArrayList<String> {
    val list = ArrayList<String>()
    file.forEach() {
        list.add(
            Compressor.compress(context = context, imageFile = File(it)).path
        )
    }
    return list
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GalleryScreen(
    onNext: (List<String>) -> Unit,
    onClose: () -> Unit
) {
    val navController = rememberNavController()
    val request = rememberPermissionState(
        permission = Manifest.permission.READ_MEDIA_IMAGES
    )
    Column {
        NavHost(
            navController = navController,
            startDestination =
            if (request.status.shouldShowRationale)
                "shouldShowRationale"
            else if (request.status.isGranted)
                "gallery"
            else "askPermission",
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable("gallery") {
                _GalleryScreen(onNext = onNext, onClose = onClose)
            }
            composable("askPermission") {
                AskPermission { request.launchPermissionRequest() }
            }
            composable("shouldShowRationale") {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "권한을 거부하였습니다. 설정화면에서 권한을 추가해주세요.", Modifier.align(Alignment.Center))
                }
            }
        }
    }
}