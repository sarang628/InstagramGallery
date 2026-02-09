package com.sarang.instagralleryModule.compose.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

typealias VideoLoadType = @Composable (VideoLoadData)->Unit

data class VideoLoadData(
    val url : String,
    val isActive : Boolean = false
)

val LocalVideoLoader = compositionLocalOf<VideoLoadType>  {
    @Composable{

    }
}