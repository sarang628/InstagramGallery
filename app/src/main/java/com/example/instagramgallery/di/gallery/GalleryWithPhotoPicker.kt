package com.example.instagramgallery.di.gallery

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickMultipleVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sarang.instagralleryModule.compose.GalleryListWithPreviewScreen

@Composable
fun GalleryWithPhotoPicker(){
    var uris : List<Uri> by remember { mutableStateOf(listOf()) }
    val pickMultipleMedia =
        rememberLauncherForActivityResult(PickMultipleVisualMedia(5)) { it ->
            if (it.isNotEmpty()) {
                Log.d("__PhotoPicker", "Number of items selected: ${it.size}")
                uris = it
            }
            else { Log.d("__PhotoPicker", "No media selected") }
        }
    GalleryListWithPreviewScreen(
        isPhotoPickerMode = true,
        list = uris.map { it.toString() },
        onPhotoPicker = { pickMultipleMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly)) })
}