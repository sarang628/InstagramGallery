package com.sarang.instagralleryModule.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.instagralleryModule.R

@Composable
fun GalleryMiddleBar(
    folder: String,                 // 폴더명
    onFolder: () -> Unit,           // 폴더 클릭
    isMutipleSelected: Boolean,     // 다중 선택 여부
    onSelectMutiple: () -> Unit,    // 다중 선택 클릭
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            Modifier
                .fillMaxHeight()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                )
                { onFolder.invoke() }, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = folder,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Image(painter = painterResource(id = R.drawable.yg), contentDescription = "")
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            SelectMutipleButton(
                onSelect = onSelectMutiple,
                isSelect = isMutipleSelected
            )
            Spacer(modifier = Modifier.width(10.dp))
            CameraButton()
        }
    }
}

@Preview
@Composable
fun PreviewGalleryMiddleBar() {
    GalleryMiddleBar(
        folder = "Folder",
        onFolder = {},
        isMutipleSelected = false,
        onSelectMutiple = {})
}