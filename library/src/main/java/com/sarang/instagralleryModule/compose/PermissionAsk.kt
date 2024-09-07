package com.sarang.instagralleryModule.compose

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AskPermission(onBack: () -> Unit) {
    val request = rememberPermissionState(permission = Manifest.permission.READ_MEDIA_IMAGES)
    AskPermission(onClick = { request.launchPermissionRequest() }, onBack = onBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AskPermission(onClick: () -> Unit, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { /*TODO*/ }, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = ""
                    )
                }
            })
        },
        contentWindowInsets = WindowInsets(bottom = 120.dp)
    ) {
        Box(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                Modifier
                    .align(Alignment.Center)
                    .padding(start = 8.dp, end = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "갤러리를 이용하기위해선 이미지 사용권한이 필요합니다.")
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {

                }) {
                    Text(text = "권한요청하기")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview(showBackground = true)
fun PreviewAskPermission() {
    AskPermission({}, {})
}