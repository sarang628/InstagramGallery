package com.sarang.instagralleryModule.util

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import com.sarang.instagralleryModule.compose.isVideo
import id.zelory.compressor.Compressor
import java.io.File

suspend fun compress(file       : List<String> = listOf(),
                     context    : Context): List<String> {
    val list = ArrayList<String>()
    file.forEach {
        if (it.startsWith("content:")) {
            val uri = it.toUri()
            //비디오라면 압축하지 않고 추가하기
            if(isVideo(context = context, uri = uri)){
                list.add(it)
            }else{
                val inputStream = context.contentResolver.openInputStream(uri)
                val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
                inputStream.use { input -> tempFile.outputStream().use { output -> input?.copyTo(output) } }
                list.add(
                    Compressor.compress(context = context, imageFile = tempFile).path
                )
            }
        } else {
            list.add(
                Compressor.compress(context = context, imageFile = File(it)).path
            )
        }
    }
    Log.d("__compress", "compressed Image : $list")
    return list
}