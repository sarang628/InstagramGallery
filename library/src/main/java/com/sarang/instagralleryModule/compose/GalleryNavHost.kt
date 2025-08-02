package com.sarang.instagralleryModule.compose

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
import com.sarang.instagralleryModule.compose.component.AskPermission
import com.sarang.instagralleryModule.viewmodel.GalleryUiState
import com.sarang.instagralleryModule.viewmodel.GalleryViewModel

/**
 * @param viewModel 갤러리 뷰모델
 * @param onNext 다음
 * @param onClose 닫기
 * @param maxCount 사진 최대 갯수
 * @param onBack 뒤로가기
 * @param galleryType 갤러리 종류
 * @param onSelectedList 선택한 파일 리스트
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun GalleryNavHost(viewModel: GalleryViewModel = hiltViewModel(), onNext: (List<String>) -> Unit = {}, onClose: () -> Unit = {}, maxCount: Int = 10, onBack: () -> Unit = {}, galleryType: Int = 0, onSelectedList: (List<String>) -> Unit = {}, isGranted: Boolean = false, shouldShowRationale : Boolean = false, onRequestPermission : () -> Unit = {}) {
    GalleryNavHost(uiState = viewModel.uiState, onNext = onNext, onClose = onClose, onBack = onBack, galleryType = galleryType, onSelectedList = onSelectedList,
        onReload = { viewModel.reload() },
        onSelectFolder = { viewModel.updateFolder(it);viewModel.closeFoldersDialog() },
        onFolder = { viewModel.openFoldersDialog() },
        onDismissRequest = { viewModel.closeFoldersDialog() },
        isGranted = isGranted,
        shouldShowRationale = shouldShowRationale,
        onRequestPermission = onRequestPermission
    )
}

/**
 * @param uiState uiState
 * @param onNext 다음
 * @param onClose 닫기
 * @param maxCount 최대 개수
 * @param onBack 뒤로 가기
 * @param onReload 갱신
 * @param onSelectFolder 폴더 선택
 * @param onFolder 폴더 선택
 * @param onDismissRequest 다이얼로그 닫기
 * @param permissionState 권한 상태
 * @param onSelectedList 파일 리스트 선택
 * @param onRequestPermission 권한 요청
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun GalleryNavHost(uiState: GalleryUiState, onNext: (List<String>) -> Unit = {}, onClose: () -> Unit = {}, maxCount: Int = 10, onBack: () -> Unit = {}, onReload: () -> Unit = {}, onSelectFolder: (String) -> Unit = {}, onFolder: () -> Unit = {}, onDismissRequest: () -> Unit = {}, isGranted: Boolean = false, shouldShowRationale : Boolean = false, galleryType: Int = 0, onSelectedList: (List<String>) -> Unit = {}, onRequestPermission : () -> Unit = {}) {
    val navController = rememberNavController()
    var isPermission by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(key1 = isGranted, block = { //권한 승인 후 사진 폴더 리스트 다시 가져오기
        if (!isGranted) { isPermission = false }
        if (isGranted && !isPermission) { onReload.invoke() }
    })

    Column {
        NavHost(
            navController = navController,
            startDestination = if (shouldShowRationale) "shouldShowRationale" else if (isGranted) "gallery" else "askPermission",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("gallery") {
                if (galleryType == 0) {
                    GalleryListWithPreviewScreen(onNext = onNext, onClose = onClose, list = uiState.list, onSelectFolder = onSelectFolder, selectedFolder = uiState.selectedFolder, onFolder = onFolder, isExpand = uiState.isExpand, onDismissRequest = onDismissRequest, folderList = uiState.folderList, maxCount = maxCount)
                } else {
                    GalleryListScreen(list = uiState.list, onSelectFolder = onSelectFolder, selectedFolder = uiState.selectedFolder, onFolder = onFolder, isExpand = uiState.isExpand, onDismissRequest = onDismissRequest, folderList = uiState.folderList, maxCount = maxCount, onSelectedList = onSelectedList)
                }
            }
            composable("askPermission") {
                AskPermission(modifier = if (galleryType != 1) Modifier.fillMaxSize() else Modifier.fillMaxWidth().height((LocalConfiguration.current.screenHeightDp * 0.7).dp), onBack = onBack, onRequestPermission = onRequestPermission)
            }
            composable("shouldShowRationale") {
                Box {
                    Box(modifier = if (galleryType != 1) Modifier.fillMaxSize().padding(horizontal = 16.dp) else Modifier.fillMaxWidth().padding(horizontal = 16.dp).height((LocalConfiguration.current.screenHeightDp * 0.7).dp),) {
                        Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "권한을 거부하였습니다. 설정화면에서 권한을 추가해주세요.")
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(onClick = { context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", context.packageName, null))) }) {
                                Text(text = "open settings") }
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
fun PreviewGalleryNavHost() {
    GalleryNavHost(
        uiState = GalleryUiState()
    )
}