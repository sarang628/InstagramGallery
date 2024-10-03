package com.sarang.instagralleryModule.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderListBottomSheetDialog(
    isExpand: Boolean,
    onSelect: (String) -> Unit,
    onDismissRequest: () -> Unit,
    list: List<String>
) {
    if (isExpand) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = rememberModalBottomSheetState(),
            content = {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn(content = {
                        items(list.size) {
                            Text(
                                text = list[it],
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        onSelect.invoke(list[it])
                                    }

                            )
                            Text(
                                text = "",
                                Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(color = Color.LightGray)
                            )
                        }
                    })
                }
            })
    }
}

@Preview
@Composable
fun PreviewFolderListBottomSheetDialog() {
    FolderListBottomSheetDialog(
        isExpand = true,
        onSelect = {},
        onDismissRequest = {},
        list = ArrayList<String>().apply {
            add("aa")
            add("bb")
            add("cc")
        }
    )
}