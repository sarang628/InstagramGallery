package com.sarang.instagralleryModule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import com.example.mediacontentresolverlibrary.MediaContentResolver

class GalleryActivity : ComponentActivity() {
    private lateinit var mediaContentResolver: MediaContentResolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaContentResolver = MediaContentResolver.newInstance(this)
        mediaContentResolver.printAvailableMediaColunm()
        mediaContentResolver.printAvailableMediaColunmWithContents()
        mediaContentResolver.requestPermission(this)

        val list = mediaContentResolver.getPictureList()

        setContent {
            Column {
                GalleryScreen()
            }
        }
    }
}