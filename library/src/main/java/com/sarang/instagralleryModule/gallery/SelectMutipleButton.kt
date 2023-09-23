package com.sarang.instagralleryModule.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sarang.instagralleryModule.R

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
