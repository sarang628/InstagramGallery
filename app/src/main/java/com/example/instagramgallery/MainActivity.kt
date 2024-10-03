package com.example.instagramgallery

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import com.sarang.instagralleryModule.GalleryNavHost
import com.sarang.instagralleryModule.PreviewGalleryNavHost
import com.sarang.torang.compose.bottomsheet.ImageSelectBottomSheetScaffold
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var show by remember { mutableStateOf(false) }
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

                    ImageSelectBottomSheetScaffold(
                        show = show,
                        onHidden = { show = false },
                        imageSelectCompose = {
                            GalleryNavHost(onNext = {
                                //selected images
                                Log.d("MainActivity", TextUtils.join(",", it))
                            }, onClose = {

                            }, onBack = {}, galleryType = 1
                            )
                        },
                        content = {
                            Button(onClick = { show = true }) {
                                Text(text = "show")
                            }
                        },
                    )
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