package com.example.instagramgallery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.instagramgallery.databinding.ActivityMainBinding
import com.example.mediacontentresolverlibrary.ImageData
import com.example.mediacontentresolverlibrary.MediaContentResolver
import com.sarang.instagralleryModule.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        dataBinding.btnGallery.setOnClickListener {
            startActivity(Intent(this, GalleryActivity::class.java))
        }
    }
}


