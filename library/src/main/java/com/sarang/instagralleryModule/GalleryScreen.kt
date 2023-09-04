package com.sarang.instagralleryModule

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun GalleryScreen(color: Long) {
    val mediaContentResolver: MediaContentResolver =
        MediaContentResolver.newInstance(LocalContext.current)

    val list = mediaContentResolver.getPictureList()

    val state = rememberLazyGridState()

    Log.d("GalleryScreen", list.toString())
    Column(Modifier.background(Color(color))) {
        //title
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
                modifier = Modifier.height(30.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "New post", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Row(Modifier.fillMaxWidth(), Arrangement.End) {
                Text(text = "Next", color = Color(0xFF4193EF))
            }
        }
        Image(
            painter = painterResource(id = R.drawable.d),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "07 toarng", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Image(painter = painterResource(id = R.drawable.yg), contentDescription = "")
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Image(painter = painterResource(id = R.drawable.b_c), contentDescription = "")
                Spacer(modifier = Modifier.width(10.dp))
                Image(painter = painterResource(id = R.drawable.cjo), contentDescription = "")
            }
        }
        Column(
            Modifier
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Fixed(4),
                state = state,
                content = {
                    items(list.size) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(list[it])
                                .build(),
                            contentDescription = "",
                        )
                    }
                })
        }
    }

}