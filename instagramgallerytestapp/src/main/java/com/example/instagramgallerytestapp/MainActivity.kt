package com.example.instagramgallerytestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sarang.instagralleryModule.InstagramGalleryContract

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getContent = registerForActivityResult(InstagramGalleryContract()) {
            it?.getStringArrayListExtra("pictures")?.also {
                /*Logger.d(it.toString())
                mViewModel.setSelectedImagePath(it)*/
            }
        }

        getContent.launch("a")
    }
}