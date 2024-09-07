package com.sarang.instagralleryModule.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.instagralleryModule.R

@Preview
@Composable
fun CameraButton() {
    FilledTonalIconButton(onClick = { /* doSomething() */ }) {
        Image(
            painter = painterResource(id = R.drawable.cjo),
            contentDescription = "",
            modifier = Modifier.size(20.dp)
        )
    }
}