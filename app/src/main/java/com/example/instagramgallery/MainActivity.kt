package com.example.instagramgallery

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.example.mediacontentresolverlibrary.MediaContentResolver
import com.sarang.instagralleryModule.InstagramGalleryContract
import com.sarang.instagralleryModule.gallery.GalleryScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mediaContentResolver = MediaContentResolver.newInstance(this)
            mediaContentResolver.requestPermission(this)
            Column {
                GalleryScreen(onNext = {
                    //selected images
                    Log.d("MainActivity", TextUtils.join(",", it))
                }, onClose = {

                })
            }
        }
    }
}