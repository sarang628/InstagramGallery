package com.example.instagramgallery

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sarang.instagralleryModule.compose.BottomSendList

@Preview(showBackground = true, backgroundColor = 0xFFEEEEEE)
@Composable
fun PreviewBottomSendList() {
    BottomSendList(selectedList = listOf(""), onSend = {})
}