package com.sarang.instagralleryModule.gallery

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun GalleryGridView(
    list: ArrayList<String>,
    onClickPicture: (String) -> Unit,
    selectedList: List<String>,
    isMutipleSelected: Boolean
) {
    val state = rememberLazyGridState()
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
                    Box {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(list[it])
                                .build(),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(1.dp)
                                .size(100.dp)
                                .clickable {
                                    onClickPicture.invoke(list[it])
                                },
                            contentScale = ContentScale.Crop
                        )
                        if (isMutipleSelected)
                            RoundCircleTag(
                                isSelected = isSelected,
                                index = if (isSelected) "${selectedList.indexOf(list[it]) + 1}" else ""
                            )
                    }
                }
            })

    }
}

