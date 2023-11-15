package com.sarang.instagralleryModule.util

import android.content.Context
import id.zelory.compressor.Compressor
import java.io.File

suspend fun compress(file: List<String>, context: Context): ArrayList<String> {
    val list = ArrayList<String>()
    file.forEach() {
        list.add(
            Compressor.compress(context = context, imageFile = File(it)).path
        )
    }
    return list
}