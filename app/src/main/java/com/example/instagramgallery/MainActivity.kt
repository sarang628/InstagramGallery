package com.example.instagramgallery

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.instagralleryModule.compose.BottomSendList
import com.sarang.instagralleryModule.compose.GalleryBottomSheet
import com.sarang.instagralleryModule.compose.PreviewGalleryNavHost
import com.sarang.torang.compose.bottomsheet.ImageSelectBottomSheetScaffold
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var show by remember { mutableStateOf(false) }
            var selectedList: List<String> by remember { mutableStateOf(listOf()) }
            TorangTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    /*GalleryNavHost(onNext = {
                        //selected images
                        Log.d("MainActivity", TextUtils.join(",", it))
                    }, onClose = {

                    }, onBack = {}, galleryType = 1
                    )*/

                    Box(modifier = Modifier.fillMaxSize())
                    {
                        GalleryBottomSheet(
                            imageSelectBottomSheetScaffold = { show, onHidden, imageSelectCompose, content ->
                                ImageSelectBottomSheetScaffold(
                                    show = show,
                                    onHidden = onHidden,
                                    imageSelectCompose = imageSelectCompose,
                                    content = content,
                                )
                            },
                            onSend = {}, onBack = {},
                            content = {
                                Box(modifier = Modifier.fillMaxSize())
                                {
                                    Button(onClick = { show = true }) {
                                        Text(text = "show")
                                    }
                                }
                            }, show = show, onHidden = {}
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun test() {
    ImageSelectBottomSheetScaffold(show = true, onHidden = { /*TODO*/ }, imageSelectCompose = {
        PreviewGalleryNavHost()
    }) {

    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEEEEE)
@Composable
fun PreviewBottomSendList() {
    BottomSendList(selectedList = listOf(""), onSend = {})
}