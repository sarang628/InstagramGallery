package com.sarang.instagralleryModule.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.instagralleryModule.R

@Composable
fun GalleryTitleBar(
    onNext: () -> Unit,
    onClose: () -> Unit,
    isAvailableNext: Boolean = false
) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter =
            painterResource(id = R.drawable.ic_close),
            contentDescription = "",
            modifier = Modifier
                .height(30.dp)
                .clickable {
                    onClose.invoke()
                }
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "New post", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Row(Modifier.fillMaxWidth(), Arrangement.End) {
            Text(text = "Next",
                color = Color(if (isAvailableNext) 0xFF4193EF else 0xFFAAAAAA),
                modifier =
                Modifier.clickable(isAvailableNext) { onNext.invoke() }
            )
        }
    }
}