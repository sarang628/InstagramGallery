package com.sarang.instagralleryModule

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.mediacontentresolverlibrary.ImageData
import com.example.mediacontentresolverlibrary.MediaContentResolver
import com.sarang.instagralleryModule.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {
    lateinit var mediaContentResolver: MediaContentResolver

    lateinit var imageAdapter: ImgAdapter

    val viewModel: GalleryViewModel by viewModels {
        GalleryViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this

        setSupportActionBar(dataBinding.tb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.b8)

        mediaContentResolver = MediaContentResolver.newInstance(this)

        mediaContentResolver.requestPermission(this)

        imageAdapter = ImgAdapter(viewModel, this).apply {
            mediaContentResolver.getPictureList().also {
                if (it.size > 0)
                    setPicturePaths(it)
            }
        }

        dataBinding.recyclerView2.adapter = imageAdapter


        // 폴더 클릭
        dataBinding.tv.setOnClickListener {
            FolderListBottomSheetDialog().show(supportFragmentManager, "dialog")
        }

        viewModel.currentSelectedImage.observe(this, Observer {
            Glide
                .with(this@GalleryActivity)
                .load(it)
                .centerCrop()
                .into(dataBinding.imageView);
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        if (fragment is FolderListBottomSheetDialog) {
            fragment.listener = object : (String) -> Unit {
                override fun invoke(p1: String) {
                    mediaContentResolver.getPictureList(p1).also {
                        if (it.size > 0)
                            imageAdapter.setPicturePaths(it)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        Log.d("__sarang", "onOptionsItemSelected ${viewModel.selectedPictures}")

        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra("pictures", viewModel.selectedPictures)
        })

        finish()

        return super.onOptionsItemSelected(item)
    }
}

object StringToImage {
    fun ArrayList<String>.toSelectImageList(): ArrayList<SelectedImage> {
        val arrayList = ArrayList<SelectedImage>()
        for (str in this) {
            arrayList.add(SelectedImage.create(0, str))
        }
        return arrayList
    }
}