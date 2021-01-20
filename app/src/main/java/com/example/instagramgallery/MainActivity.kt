package com.example.instagramgallery

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramgallery.databinding.ActivityMainBinding
import com.example.mediacontentresolverlibrary.MediaContentResolver


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

        viewBinding.recyclerView2.adapter = ImgAdapter().apply {
            setPicturePaths(mediaContentResolver.getPictureList())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

class ImgAdapter : RecyclerView.Adapter<ImageViewHolder>(){
    var picturePath = ArrayList<String>()

    fun setPicturePaths(list : ArrayList<String>){
        picturePath = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_img, parent, false))
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
    }
}

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

}