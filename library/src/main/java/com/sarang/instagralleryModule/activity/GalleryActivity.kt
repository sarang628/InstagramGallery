package com.sarang.instagralleryModule.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column

class GalleryActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {

            }
        }
    }
}

fun ComponentActivity.instagramGallery() {
    val contract = registerForActivityResult(InstagramGalleryContract()) {
        Log.d("__GalleryActivity", "registerForActivityResult");
    }

    contract.launch("")
}