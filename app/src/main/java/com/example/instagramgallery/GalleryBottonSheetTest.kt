package com.example.instagramgallery

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sarang.instagralleryModule.compose.GalleryBottomSheet

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun GalleryBottonSheetTest(){
    var show by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize())
    {
        GalleryBottomSheet(
            imageSelectBottomSheetScaffold = { show, onHidden, imageSelectCompose, content ->
                //PickHeight70PercentBottomSheetScaffold(show = show, onHidden = onHidden, imageSelectCompose = imageSelectCompose, content = content)
            },
            content = { Box(modifier = Modifier.fillMaxSize()) { Button(onClick = { show = true }) { Text(text = "show") } } },
            show = show,
            onHidden = { show = false }
        )
    }
}