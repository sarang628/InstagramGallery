package com.sarang.instagralleryModule.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    folder: String,
    onFolder: (Void?) -> Unit,
    isMutipleSelected: Boolean,
    onSelectMutiple: (Void?) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = folder,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.clickable {
                onFolder.invoke(null)
            }
        )
        Image(painter = painterResource(id = R.drawable.yg), contentDescription = "")
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            SelectMutipleButton(
                onSelectMutiple = onSelectMutiple,
                isMutipleSelected = isMutipleSelected
            )
            Spacer(modifier = Modifier.width(10.dp))
            CameraButton()
        }
    }
}

@Preview
@Composable
fun PreviewGalleryMiddleBar() {
    GalleryMiddleBar(folder = "", onFolder = {}, isMutipleSelected = false, onSelectMutiple = {})
}