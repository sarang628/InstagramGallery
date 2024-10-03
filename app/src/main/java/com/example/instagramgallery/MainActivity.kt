package com.example.instagramgallery

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
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
                        ImageSelectBottomSheetScaffold(
                            show = show,
                            onHidden = {
                                show = false
                                selectedList = listOf()
                            },
                            imageSelectCompose = {
                                if (show)
                                    GalleryNavHost(onNext = {
                                        //selected images
                                        Log.d("MainActivity", TextUtils.join(",", it))
                                    }, onClose = {

                                    }, onBack = {},
                                        galleryType = 1,
                                        onSelectedList = {
                                            selectedList = it
                                        }
                                    )
                            },
                            content = {
                                Button(onClick = { show = true }) {
                                    Text(text = "show")
                                }
                            },
                        )

                        if (selectedList.isNotEmpty())
                            BottomSendList(
                                modifier = Modifier
                                    .align(Alignment.BottomStart),
                                selectedList = selectedList,
                                onSend = {

                                }
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

@Composable
fun BottomSendList(modifier: Modifier = Modifier, selectedList: List<String>, onSend: () -> Unit) {
    Box(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(
                Color(0xCCFFFFFF)
            )
    )
    {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth(),
            constraintSet = ConstraintSet {
                val sendButton = createRefFor("sendButton")
                val sendList = createRefFor("sendList")

                constrain(sendButton) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }

                constrain(sendList) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(sendButton.start)
                    width = Dimension.fillToConstraints
                }

            }
        ) {
            LazyRow(
                modifier = Modifier
                    .layoutId("sendList")
                    .fillMaxWidth()
                    .height(100.dp),
                contentPadding = PaddingValues(horizontal = 5.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(selectedList.size) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .height(80.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .width(50.dp)
                                .height(80.dp),
                            model = selectedList[it],
                            contentDescription = ""
                        )
                    }
                }
            }
            Button(
                modifier = Modifier.layoutId("sendButton"),
                onClick = onSend
            ) {
                Text(text = "Send")
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEEEEE)
@Composable
fun PreviewBottomSendList() {
    BottomSendList(selectedList = listOf(""), onSend = {})
}