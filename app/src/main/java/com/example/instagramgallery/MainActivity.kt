package com.example.instagramgallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramgallery.StringToImage.toSelectImageList
import com.example.instagramgallery.databinding.ActivityMainBinding
import com.example.instagramgallery.databinding.ItemImgBinding
import com.example.mediacontentresolverlibrary.ImageData
import com.example.mediacontentresolverlibrary.MediaContentResolver

lateinit var mediaContentResolver: MediaContentResolver

class MainActivity : AppCompatActivity() {
    lateinit var imageAdapter: ImgAdapter

    val viewModel: GalleryViewModel by viewModels {
        GalleryViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding = ActivityMainBinding.inflate(layoutInflater)
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
                .with(this@MainActivity)
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
            fragment.listener = object : (ImageData) -> Unit {
                override fun invoke(p1: ImageData) {
                    mediaContentResolver.getPictureList(p1.bucketDisplayName).also {
                        if (it.size > 0)
                            imageAdapter.setPicturePaths(it)
                    }
                }
            }
        }
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

class ImgAdapter(
    private val galleryViewModel: GalleryViewModel,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<ImageViewHolder>() {
    var picturePath = ArrayList<String>()
    fun setPicturePaths(list: ArrayList<String>) {
        picturePath = list
        galleryViewModel.selectImage(list[0])
        notifyDataSetChanged()
        galleryViewModel.setPicturepaths(picturePath)

        //갯수만큼 라이브데이터를 만든다?
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_img, parent, false),
            galleryViewModel,
            lifecycleOwner
        )
    }

    override fun getItemCount(): Int {
        return picturePath.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide
            .with(holder.itemView.context)
            .load(picturePath[position])
            .centerCrop()
            .into(holder.itemView.findViewById(R.id.iv));

        holder.itemView.setOnClickListener {
            galleryViewModel.selectImage(picturePath[position])
        }

        holder.binding(position)
    }
}

class ImageViewHolder(
    itemView: View,
    private val galleryViewModel: GalleryViewModel,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(itemView) {
    private val itemImgBinding = ItemImgBinding.bind(itemView).apply {
        viewModel = galleryViewModel
        lifecycleOwner = this@ImageViewHolder.lifecycleOwner
    }

    fun binding(position: Int) {
        itemImgBinding.selectedImage = galleryViewModel.picturesLiveData[position]
    }
}
