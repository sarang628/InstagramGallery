package com.sarang.instagralleryModule

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mediacontentresolverlibrary.MediaContentResolver


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FolderListBottomSheetDialog(
    isExpand: Boolean,
    onSelect: (String) -> Unit
) {
    val mediaContentResolver = MediaContentResolver.newInstance(LocalContext.current)
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    val list = mediaContentResolver.getFolderList()

    LaunchedEffect(key1 = "", block = {
        if (isExpand) {
            Log.d("FolderListBottomSheetDialog", "!!!!")
//            scaffoldState.bottomSheetState.partialExpand()
            scaffoldState.bottomSheetState.expand()
        }
    })


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
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
        }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            Text("Scaffold Content")
        }
    }

}