package com.sarang.instagralleryModule.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sarang.instagralleryModule.R

@Composable
fun SelectMutipleButton(
    onSelect: () -> Unit,
    isSelect: Boolean
) {
    FilterChip(
        selected = isSelect,
        onClick = onSelect,
        label = { Text("SELECT MUTIPLE") },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.b_c),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
        }
    )

}

@Preview
@Composable
fun PreviewSelectMutipleButton() {
    SelectMutipleButton(onSelect = {}, isSelect = true)
}

@Preview
@Composable
fun PreviewSelectMutipleButton1() {
    SelectMutipleButton(onSelect = {}, isSelect = false)
}