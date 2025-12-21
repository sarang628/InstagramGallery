package com.sarang.instagralleryModule.compose.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryTitleBar(
    onNext          : () -> Unit    = {},
    onClose         : () -> Unit    = {},
    isAvailableNext : Boolean       = false
) {
    TopAppBar(title = { Text("New post") },
              navigationIcon = {
                  IconButton(onClick = onClose) {
                      Icon(imageVector = Icons.Default.Close,
                           contentDescription = null)
                  }
              },
        actions = {
            TextButton(onClick = { onNext.invoke() }) {
                Text(text = "Next",
                     color = if (isAvailableNext) MaterialTheme.colorScheme.primary else Color(0xFFAAAAAA),
                    )
            }
        }
    )
}