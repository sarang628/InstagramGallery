package com.example.instagramgallery

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instagramgallery.di.Instagramgallery_di.GalleryWithPhotoPicker
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
                            Menu(navController = navController)
                        }
                        composable("GalleryWithPermission"){
                            //GalleryWithPermission(viewModel = BestPracticeViewModel())
                        }
                        composable("GalleryBottonSheetTest"){
                            GalleryBottonSheetTest()
                        }
                        composable("GalleryWithPhotoPicker"){
                            GalleryWithPhotoPicker()
                        }
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun Menu(navController : NavHostController = rememberNavController()){
        Column {
            Button({navController.navigate("GalleryWithPermission")}) {
                Text("GalleryWithPermission")
            }
            Button({navController.navigate("GalleryBottonSheetTest")}) {
                Text("GalleryBottonSheetTest")
            }
            Button({navController.navigate("GalleryWithPhotoPicker")}) {
                Text("GalleryWithPhotoPicker")
            }
        }
    }
}