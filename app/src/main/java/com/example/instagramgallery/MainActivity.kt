package com.example.instagramgallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramgallery.databinding.ActivityMainBinding
import com.example.mediacontentresolverlibrary.MediaContentResolver
import com.google.android.material.bottomsheet.BottomSheetDialog


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setSupportActionBar(viewBinding.tb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.b8)

        val mediaContentResolver = MediaContentResolver.newInstance(this)

        mediaContentResolver.requestPermission(this)

        viewBinding.recyclerView2.adapter = ImgAdapter(object : ((String) -> Unit) {
            override fun invoke(p1: String) {
                Glide
                    .with(this@MainActivity)
                    .load(p1)
                    .centerCrop()
                    .into(viewBinding.imageView);
            }
        }).apply {
            setPicturePaths(mediaContentResolver.getPictureList())
        }


        viewBinding.tv.setOnClickListener {
            FolderListBottomSheetDialog().show(supportFragmentManager, "dialog")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

class ImgAdapter(val listener: (url: String) -> Unit) : RecyclerView.Adapter<ImageViewHolder>() {
    var picturePath = ArrayList<String>()

    fun setPicturePaths(list: ArrayList<String>) {
        picturePath = list
        listener.invoke(list[0])
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_img, parent, false)
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
            listener.invoke(picturePath[position])
        }
    }
}

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}
