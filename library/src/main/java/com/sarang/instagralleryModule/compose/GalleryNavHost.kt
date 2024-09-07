package com.sarang.instagralleryModule

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.sarang.instagralleryModule.compose.AskPermission
import com.sarang.instagralleryModule.compose.GalleryScreen
import com.sarang.instagralleryModule.viewmodel.GalleryViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GalleryNavHost(
    viewModel: GalleryViewModel = hiltViewModel(),
    onNext: (List<String>) -> Unit,
    onClose: () -> Unit,
    maxCount: Int = 10,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val request = rememberPermissionState(permission = Manifest.permission.READ_MEDIA_IMAGES)
    var isPermission by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = request.status.isGranted, block = {
        //권한 승인 후 사진 폴더 리스트 다시 가져오기
        if (!request.status.isGranted) {
            isPermission = false
        }

        if (request.status.isGranted && !isPermission) {
            viewModel.reLoad()
        }
    })

    Column {
        NavHost(
            navController = navController,
            startDestination =
            if (request.status.shouldShowRationale)
                "shouldShowRationale"
            else if (request.status.isGranted)
                "gallery"
            else "askPermission",
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable("gallery") {
                GalleryScreen(
                    onNext = onNext, onClose = onClose, list = uiState.list, onSelectFolder = {
                        viewModel.updateFolder(it)
                        viewModel.closeFoldersDialog()
                    },
                    selectedFolder = uiState.selectedFolder,
                    onFoler = { viewModel.openFoldersDialog() },
                    isExpand = uiState.isExpand,
                    onDismissRequest = { viewModel.closeFoldersDialog() },
                    folderList = uiState.folderList,
                    maxCount = maxCount
                )
            }
            composable("askPermission") {
                AskPermission(onBack = onBack)
            }
            composable("shouldShowRationale") {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "권한을 거부하였습니다. 설정화면에서 권한을 추가해주세요.", Modifier.align(Alignment.Center))
                }
            }
        }
    }
}