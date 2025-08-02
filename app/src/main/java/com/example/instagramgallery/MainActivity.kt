package com.example.instagramgallery

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.instagralleryModule.compose.BottomSendList
import com.sarang.instagralleryModule.compose.GalleryBottomSheet
import com.sarang.instagralleryModule.compose.GalleryNavHost
import com.sarang.instagralleryModule.compose.PreviewGalleryNavHost
import com.sarang.torang.compose.bottomsheet.PickHeight70PercentBottomSheetScaffold
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "init"){
                        composable("init"){
                            Column {
                                Button({navController.navigate("GalleryNavHost")}) {
                                    Text("GalleryNavHost")
                                }
                                Button({navController.navigate("GalleryBottonSheetTest")}) {
                                    Text("GalleryBottonSheetTest")
                                }
                            }
                        }
                        composable("GalleryNavHost"){
                            GalleryNavHost(onNext = { Log.d("__MainActivity", TextUtils.join(",", it)) })
                        }
                        composable("GalleryBottonSheetTest"){
                            GalleryBottonSheetTest()
                        }
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
    PickHeight70PercentBottomSheetScaffold(show = true, onHidden = { /*TODO*/ }, imageSelectCompose = {
        PreviewGalleryNavHost()
    }) {

    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEEEEE)
@Composable
fun PreviewBottomSendList() {
    BottomSendList(selectedList = listOf(""), onSend = {})
}

@Composable
fun GalleryBottonSheetTest(){
    var show by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize())
    {
        GalleryBottomSheet(
            imageSelectBottomSheetScaffold = { show, onHidden, imageSelectCompose, content ->
                PickHeight70PercentBottomSheetScaffold(show = show, onHidden = onHidden, imageSelectCompose = imageSelectCompose, content = content)
            },
            content = { Box(modifier = Modifier.fillMaxSize()) { Button(onClick = { show = true }) { Text(text = "show") } } },
            show = show,
            onHidden = { show = false }
        )
    }
}