package com.sarang.instagralleryModule.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import com.example.mediacontentresolverlibrary.MediaContentResolver
import com.sarang.instagralleryModule.GalleryNavHost

class GalleryActivity : ComponentActivity() {
    private lateinit var mediaContentResolver: MediaContentResolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val list = mediaContentResolver.getPictureList()
        setContent {
            Column {
                GalleryNavHost(onNext = {
                }, onClose = {})
            }
        }
    }
}

fun ComponentActivity.instagramGallery() {
    val contract = registerForActivityResult(InstagramGalleryContract()) {
        Log.d("_sryang", "registerForActivityResult");
    }

    contract.launch("")
}