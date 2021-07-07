package com.example.instagramgallery

import android.app.Activity
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

    lateinit var dataBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        val contract = registerForActivityResult(InstagramGalleryContract()){
            Log.d("_sryang", "registerForActivityResult");
            it?.getStringArrayListExtra("pictures")?.also {
                dataBinding.tvPictures.text = it.toString()
            }
        }

        dataBinding.btnGallery.setOnClickListener {
            //startActivityForResult(Intent(this, GalleryActivity::class.java), 1)
            contract.launch("a")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == Activity.RESULT_OK)
        data?.also {
            it.getStringArrayListExtra("pictures")?.also {
                dataBinding.tvPictures.text = it.toString()
            }
        }
    }
}


