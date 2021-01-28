package com.example.instagramgallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {
    private val _isMultiSelect = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isMultiSelect : LiveData<Boolean> = _isMultiSelect

    fun clickMultiSelect(){
        _isMultiSelect.value = !_isMultiSelect.value!!
    }
}