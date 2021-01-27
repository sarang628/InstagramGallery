package com.example.instagramgallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {
    val isMultiSelect = MutableLiveData<Boolean>()
}