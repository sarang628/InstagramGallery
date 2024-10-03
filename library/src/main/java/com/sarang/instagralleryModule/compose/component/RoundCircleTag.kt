package com.sarang.instagralleryModule.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoundCircleTag(isSelected: Boolean, index: String) {
    Box() {
        Box(
            modifier = Modifier
                .padding(6.dp)
                .size(30.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected)
                        Color(0xFFFFFFFF)
                    else
                        Color(0x55FFFFFF)
                )
                .align(Alignment.TopEnd),
        )
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(26.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        Color(0x55FFFFFF)
                )
                .align(Alignment.TopEnd),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = index,
                color = Color(0xFFFFFFFF)
            )
        }
    }
}

@Preview
@Composable
fun PreviewRoundCircleTag() {
    RoundCircleTag(isSelected = true, index = "1")
}

@Preview
@Composable
fun PreviewRoundCircleTag1() {
    RoundCircleTag(isSelected = true, index = "20")
}