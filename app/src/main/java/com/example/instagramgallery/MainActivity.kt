package com.example.instagramgallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.sarang.instagralleryModule.InstagramGalleryContract

class MainActivity : ComponentActivity() {
    //
//    lateinit var dataBinding : ActivityMainBinding
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val textPicture by remember { mutableStateOf("") }

        val contract = registerForActivityResult(InstagramGalleryContract()) {
            Log.d("_sryang", "registerForActivityResult");
            it?.getStringArrayListExtra("pictures")?.also {
                //dataBinding.tvPictures.text = it.toString()
            }
        }

        setContent {
            Button(onClick = {
                contract.launch("a")
            }) {
                Text(text = "!")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK)
            data?.also {
                it.getStringArrayListExtra("pictures")?.also {

                }
            }
    }
}