package com.example.instagramgallery

import android.Manifest
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.sarang.instagralleryModule.gallery.AskPermission
import com.sarang.instagralleryModule.gallery.GalleryScreen
import com.sarang.instagralleryModule.go

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
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