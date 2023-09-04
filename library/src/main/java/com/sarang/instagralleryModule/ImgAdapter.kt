package com.sarang.instagralleryModule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide

//class ImgAdapter(
//    private val galleryViewModel: GalleryViewModel,
//    private val lifecycleOwner: LifecycleOwner
//) : RecyclerView.Adapter<ImageViewHolder>() {
//    var picturePath = ArrayList<String>()
//    fun setPicturePaths(list: ArrayList<String>) {
//        picturePath = list
//        galleryViewModel.selectImage(list[0])
//        notifyDataSetChanged()
//        galleryViewModel.setPicturepaths(picturePath)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        return ImageViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.item_img, parent, false),
//            galleryViewModel,
//            lifecycleOwner
//        )
//    }
//
//    override fun getItemCount(): Int {
//        return picturePath.size
//    }
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        Glide
//            .with(holder.itemView.context)
//            .load(picturePath[position])
//            .centerCrop()
//            .into(holder.itemView.findViewById(R.id.iv));
//
//        holder.itemView.setOnClickListener {
//            galleryViewModel.selectImage(picturePath[position])
//        }
//
//        holder.binding(position)
//    }
//}