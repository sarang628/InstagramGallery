package com.sarang.instagralleryModule.gallery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AskPermission(onRequest: () -> Unit) {

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "갤러리를 이용하기위해선 이미지 사용권한이 필요합니다.")
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                onRequest.invoke()
            }) {
                Text(text = "권한요청하기")
            }
        }
    }
}