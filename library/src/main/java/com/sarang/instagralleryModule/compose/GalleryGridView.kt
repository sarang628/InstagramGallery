package com.sarang.instagralleryModule.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun GalleryGridView(
    list: List<String>,                 // 이미지 리스트
    onClickPicture: (String) -> Unit,   // 이미지 클릭
    selectedList: List<String>,         // 이미지 선택 리스트
    isMutipleSelected: Boolean          // 이미지 다중 선택 여부
) {
    val state = rememberLazyGridState()
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        Modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(1.dp),
            state = state,
            content = {
                items(list.size) {
                    val isSelected = selectedList.contains(list[it])
                    Card(shape = RectangleShape) {
                        Box {
                            val interactionSource = remember { MutableInteractionSource() }
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(list[it])
                                    .build(),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(1.dp)
                                    .size(100.dp)
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = rememberRipple(true), onClick = {
                                            onClickPicture.invoke(list[it])
                                        }),
                                contentScale = ContentScale.Crop
                            )
                            if (isMutipleSelected)
                                RoundCircleTag(
                                    isSelected = isSelected,
                                    index = if (isSelected) "${selectedList.indexOf(list[it]) + 1}" else ""
                                )
                        }
                    }
                }
            })

    }
}


@Preview
@Composable
fun PreviewGalleryGridView() {
    GalleryGridView(
        list = ArrayList<String>().apply {
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
            add("")
        },
        onClickPicture = {},
        selectedList = ArrayList(),
        isMutipleSelected = true
    )
}
