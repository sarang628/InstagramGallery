package com.sarang.instagralleryModule

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.mediacontentresolverlibrary.MediaContentResolver
import com.sarang.instagralleryModule.gallery.GalleryScreen

class GalleryActivity : ComponentActivity() {
    private lateinit var mediaContentResolver: MediaContentResolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val list = mediaContentResolver.getPictureList()
        setContent {
            Column {
                GalleryScreen(0xFF000000, onNext = {
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

fun NavHostController.go(route: String) {
    navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}