package com.sarang.instagralleryModule

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.sarang.instagralleryModule.databinding.ItemImgBinding

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