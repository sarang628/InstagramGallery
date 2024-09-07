package com.sarang.instagralleryModule.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.instagralleryModule.R

@Composable
fun GalleryTitleBar(
    onNext: () -> Unit,                 // next 클릭
    onClose: () -> Unit,                // 닫기 클릭
    isAvailableNext: Boolean = false,    // next 활성화 여부
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onClose) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "",
                modifier = Modifier
                    .height(30.dp)
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "New post", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Row(Modifier.fillMaxWidth(), Arrangement.End) {
            Text(text = "Next",
                color = if (isAvailableNext) MaterialTheme.colorScheme.primary else Color(0xFFAAAAAA),
                modifier =
                Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = isAvailableNext
                ) { onNext.invoke() }
            )
        }
    }
}

@Preview
@Composable
fun PreviewGalleryTitleBar() {
    GalleryTitleBar(onNext = { /*TODO*/ }, onClose = { /*TODO*/ })
}
