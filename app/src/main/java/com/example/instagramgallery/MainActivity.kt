package com.example.instagramgallery

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instagramgallery.di.gallery.GalleryWithPermission
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.sarang.instagralleryModule.compose.BottomSendList
import com.sarang.instagralleryModule.compose.GalleryBottomSheet
import com.sarang.instagralleryModule.compose.GalleryListWithPreviewScreen
import com.sarang.instagralleryModule.compose.component.AskPermission
import com.sarang.torang.compose.bottomsheet.PickHeight70PercentBottomSheetScaffold
import com.sryang.library.compose.workflow.BestPracticeViewModel
import com.sryang.library.compose.workflow.DescribePermissionDialog
import com.sryang.library.compose.workflow.MoveSystemSettingDialog
import com.sryang.library.compose.workflow.PermissonWorkFlow.CheckRationale
import com.sryang.library.compose.workflow.PermissonWorkFlow.DeniedPermission
import com.sryang.library.compose.workflow.PermissonWorkFlow.GrantedPermission
import com.sryang.library.compose.workflow.PermissonWorkFlow.InitialPermissionCheck
import com.sryang.library.compose.workflow.PermissonWorkFlow.RecognizeToUser
import com.sryang.library.compose.workflow.PermissonWorkFlow.RequestPermission
import com.sryang.library.compose.workflow.PermissonWorkFlow.ShowRationale
import com.sryang.library.compose.workflow.PermissonWorkFlow.SuggestSystemSetting
import com.sryang.library.compose.workflow.RationaleDialog
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
                                Button({navController.navigate("GalleryWithPermission")}) {
                                    Text("GalleryWithPermission")
                                }
                                Button({navController.navigate("GalleryBottonSheetTest")}) {
                                    Text("GalleryBottonSheetTest")
                                }
                            }
                        }
                        composable("GalleryWithPermission"){
                            GalleryWithPermission(viewModel = BestPracticeViewModel())
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