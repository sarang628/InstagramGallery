package com.sarang.instagralleryModule.compose

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AskPermission() {
    val request = rememberPermissionState(permission = Manifest.permission.READ_MEDIA_IMAGES)
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .align(Alignment.Center)
                .padding(start = 8.dp, end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "갤러리를 이용하기위해선 이미지 사용권한이 필요합니다.")
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                request.launchPermissionRequest()
            }) {
                Text(text = "권한요청하기")
            }
        }
    }
}

@Composable
@Preview
fun PreviewAskPermission() {
    AskPermission()
}