package com.sarang.instagralleryModule.compose.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

typealias ImageLoadType = @Composable (ImageLoadData)->Unit

data class ImageLoadData(
    val modifier        : Modifier      = Modifier,
    val url             : String        = "",
    val contentScale    : ContentScale  = ContentScale.None
)

val LocalImageLoader = compositionLocalOf<ImageLoadType>  {
    @Composable{

    }
}