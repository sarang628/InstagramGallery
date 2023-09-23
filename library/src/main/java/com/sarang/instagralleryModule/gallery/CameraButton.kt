package com.sarang.instagralleryModule.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.instagralleryModule.R

@Preview
@Composable
fun CameraButton() {
    val shape = RoundedCornerShape(20.dp)
    Row(
        modifier = Modifier
            .height(35.dp)
            .clip(shape)
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(Modifier.padding(start = 10.dp, end = 10.dp)) {
            Image(
                painter = painterResource(id = R.drawable.cjo),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}