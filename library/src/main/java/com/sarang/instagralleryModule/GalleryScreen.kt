package com.sarang.instagralleryModule

import android.content.Context
import android.util.Log
import android.view.RoundedCorner
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mediacontentresolverlibrary.MediaContentResolver

@Composable
fun GalleryScreen(
    color: Long,
    onNext: (List<String>) -> Unit,
    onClose: (Void?) -> Unit
) {
    val mediaContentResolver: MediaContentResolver =
        MediaContentResolver.newInstance(LocalContext.current)

    var list by remember { mutableStateOf(mediaContentResolver.getPictureList()) }
    var selectedImage by remember { mutableStateOf("") }
    var isExpand by remember { mutableStateOf(false) }
    var selectedFolder by remember { mutableStateOf("Recent") }
    val selectedList = remember { mutableStateListOf<String>() }
    var isMutipleSelected by remember { mutableStateOf(false) }



    Box {
        Column(Modifier.background(Color(color))) {
            //titlebar
            GalleryTitleBar(
                onNext = {
                    onNext.invoke(if (isMutipleSelected) selectedList else ArrayList(selectedList))
                },
                onClose = onClose
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(selectedImage)
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                //contentScale = ContentScale.Crop
            )
            //center of gallery menu
            GalleryMiddleBar(
                folder = selectedFolder,
                isMutipleSelected = isMutipleSelected,
                onFolder = {
                    Log.d("GalleryScreen", "!!!!!!")
                    isExpand = !isExpand
                },
                onSelectMutiple = {
                    isMutipleSelected = !isMutipleSelected
                }
            )
            GalleryGridView(list = list,
                isMutipleSelected = isMutipleSelected,
                selectedList = selectedList,
                onClickPicture = {
                    selectedImage = it

                    if (isMutipleSelected) {
                        if (!selectedList.contains(it)) {
                            selectedList.add(it)
                        } else {
                            selectedList.remove(it)
                        }
                    }
                })
        }
        if (isExpand)
            FolderListBottomSheetDialog(isExpand) {
                selectedFolder = it
                list = mediaContentResolver.getPictureList(it)
                isExpand = false
            }
    }
}

@Preview
@Composable
fun PreviewGalleryScreen() {
    val context = LocalContext.current
    GalleryScreen(color = 0xFFFFFFFF, onNext = {
        Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
    }, onClose = {

    })
}


@Composable
fun GalleryTitleBar(
    onNext: (Void?) -> Unit,
    onClose: (Void?) -> Unit
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
                    onClose.invoke(null)
                }
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "New post", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Row(Modifier.fillMaxWidth(), Arrangement.End) {
            Text(text = "Next",
                color = Color(0xFF4193EF),
                modifier = Modifier.clickable {
                    onNext.invoke(null)
                }
            )
        }
    }
}

@Composable
fun GalleryMiddleBar(
    folder: String,
    onFolder: (Void?) -> Unit,
    isMutipleSelected: Boolean,
    onSelectMutiple: (Void?) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = folder,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.clickable {
                onFolder.invoke(null)
            }
        )
        Image(painter = painterResource(id = R.drawable.yg), contentDescription = "")
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            SelectMutipleButton(
                onSelectMutiple = onSelectMutiple,
                isMutipleSelected = isMutipleSelected
            )
            Spacer(modifier = Modifier.width(10.dp))
            CameraButton()
        }
    }
}

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
                        Color(0xFF4193EF)
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

@Composable
fun SelectMutipleButton(
    onSelectMutiple: (Void?) -> Unit,
    isMutipleSelected: Boolean
) {
    val shape = RoundedCornerShape(20.dp)
    Row(
        modifier = Modifier
            .height(35.dp)
            .clip(shape)
            .background(
                if (isMutipleSelected)
                    Color(0xFF4193EF)
                else
                    Color.LightGray
            )
            .clickable { onSelectMutiple.invoke(null) },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(Modifier.padding(start = 10.dp, end = 10.dp)) {
            Image(
                painter = painterResource(id = R.drawable.b_c),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "SELECT MUTIPLE")
        }
    }
}

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