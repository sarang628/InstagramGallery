package com.sarang.instagralleryModule.compose.component

data class GalleryListWithPreviewUIState(val isProgress          : Boolean              = false,
                                         val isMutipleSelected   : Boolean              = false,
                                         val selectedImage       : String               = "",
                                         val selectedList        : MutableList<String>  = mutableListOf(),
                                         val maxCount            : Int                  = 0,
                                         val compressedImages    : List<String>         = listOf())


val GalleryListWithPreviewUIState.files: List<String> get() {
    return if (isMutipleSelected) selectedList
    else ArrayList<String>().apply {
        if(selectedImage.isNotEmpty())
            add(selectedImage)
    }
}