package com.sarang.instagralleryModule.compose

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.sarang.instagralleryModule.compose.component.AskPermission
import com.sarang.instagralleryModule.viewmodel.GalleryUiState
import com.sarang.instagralleryModule.viewmodel.GalleryViewModel

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun GalleryNavHost(
    viewModel: GalleryViewModel = hiltViewModel(),
    onNext: (List<String>) -> Unit,
    onClose: () -> Unit,
    maxCount: Int = 10,
    onBack: () -> Unit,
    galleryType: Int = 0,
    onSelectedList: (List<String>) -> Unit = {},
) {
    val uiState = viewModel.uiState

    GalleryNavHost(
        uiState = uiState,
        onNext = onNext,
        onClose = onClose,
        onBack = onBack,
        onReLoad = { viewModel.reLoad() },
        onSelectFolder = {
            viewModel.updateFolder(it)
            viewModel.closeFoldersDialog()
        },
        onFoler = { viewModel.openFoldersDialog() },
        onDismissRequest = { viewModel.closeFoldersDialog() },
        permissionState = rememberPermissionState(permission = Manifest.permission.READ_MEDIA_IMAGES),
        galleryType = galleryType,
        onSelectedList = onSelectedList
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun GalleryNavHost(
    uiState: GalleryUiState,
    onNext: (List<String>) -> Unit,
    onClose: () -> Unit,
    maxCount: Int = 10,
    onBack: () -> Unit,
    onReLoad: () -> Unit,
    onSelectFolder: (String) -> Unit,
    onFoler: () -> Unit,                // 폴더 리스트 다이얼로그 클릭
    onDismissRequest: () -> Unit,       // 폴더 리스트 다이얼로그 닫기 이벤트,
    permissionState: PermissionState,
    galleryType: Int = 0,
    onSelectedList: (List<String>) -> Unit = {},
) {
    val navController = rememberNavController()
    var isPermission by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(key1 = permissionState.status.isGranted, block = {
        //권한 승인 후 사진 폴더 리스트 다시 가져오기
        if (!permissionState.status.isGranted) {
            isPermission = false
        }

        if (permissionState.status.isGranted && !isPermission) {
            onReLoad.invoke()
        }
    })

    Column {
        NavHost(
            navController = navController,
            startDestination =
            if (permissionState.status.shouldShowRationale)
                "shouldShowRationale"
            else if (permissionState.status.isGranted)
                "gallery"
            else "askPermission",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("gallery") {
                if (galleryType == 0) {
                    GalleryListWithPreviewScreen(
                        onNext = onNext,
                        onClose = onClose,
                        list = uiState.list,
                        onSelectFolder = onSelectFolder,
                        selectedFolder = uiState.selectedFolder,
                        onFoler = onFoler,
                        isExpand = uiState.isExpand,
                        onDismissRequest = onDismissRequest,
                        folderList = uiState.folderList,
                        maxCount = maxCount
                    )
                } else {
                    GalleryListScreen(
                        list = uiState.list,
                        onSelectFolder = onSelectFolder,
                        selectedFolder = uiState.selectedFolder,
                        onFoler = onFoler,
                        isExpand = uiState.isExpand,
                        onDismissRequest = onDismissRequest,
                        folderList = uiState.folderList,
                        maxCount = maxCount,
                        onSelectedList = onSelectedList
                    )
                }
            }
            composable("askPermission") {
                AskPermission(
                    modifier = if (galleryType != 1) Modifier.fillMaxSize()
                    else Modifier
                        .fillMaxWidth()
                        .height((LocalConfiguration.current.screenHeightDp * 0.7).dp),
                    onBack = onBack,
                    permissionState = permissionState
                )
            }
            composable("shouldShowRationale") {
                Box {
                    Box(
                        modifier = if (galleryType != 1) Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                        else Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height((LocalConfiguration.current.screenHeightDp * 0.7).dp),
                    ) {
                        Column(
                            Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "권한을 거부하였습니다. 설정화면에서 권한을 추가해주세요.")
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(onClick = {
                                val intent = Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", context.packageName, null)
                                )
                                context.startActivity(intent)
                            }) {
                                Text(text = "open settings")
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun PreviewGalleryNavHost() {
    GalleryNavHost(
        uiState = GalleryUiState(),
        onNext = {},
        onClose = { /*TODO*/ },
        onBack = { /*TODO*/ },
        onReLoad = { /*TODO*/ },
        onSelectFolder = {},
        onFoler = { /*TODO*/ },
        onDismissRequest = { /*TODO*/ },
        permissionState = object : PermissionState {
            override val permission: String
                get() = ""
            override val status: PermissionStatus
                get() = PermissionStatus.Granted

            override fun launchPermissionRequest() {

            }

        },
        galleryType = 1
    )
}