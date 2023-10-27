package com.example.instagramgallery

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sarang.instagralleryModule.gallery.GalleryScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryScreen(onNext = {
                //selected images
                Log.d("MainActivity", TextUtils.join(",", it))
            }, onClose = {

            }, color = 0XFFEEAAFF)
        }
    }
}