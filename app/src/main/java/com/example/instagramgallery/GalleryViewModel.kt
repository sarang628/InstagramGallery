package com.example.instagramgallery

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.instagramgallery.StringToImage.toSelectImageList
import com.example.mediacontentresolverlibrary.ImageData
import com.example.mediacontentresolverlibrary.MediaContentResolver

class GalleryViewModel(val mediaContentResolver: MediaContentResolver) : ViewModel() {
    private val _isMultiSelect = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isMultiSelect: LiveData<Boolean> = _isMultiSelect
    private val _currentSelectedImage = MutableLiveData<String>()
    private val selectedPictures = ArrayList<SelectedImage>()
    val currentSelectedImage: LiveData<String> = _currentSelectedImage
    var picturesLiveData = ArrayList<SelectedImage>()

    fun selectImage(path: String) {
        _currentSelectedImage.value = path
        for (data in picturesLiveData) {
            data.index.postValue(data.index.value?.plus(1))
        }
    }


    fun clickMultiSelect() {
        _isMultiSelect.value = !_isMultiSelect.value!!
    }

    fun selectPicture(path: String) {
        selectedPictures.add(SelectedImage.create(selectedPictures.size, path))
    }

    fun setPicturepaths(picturePath: java.util.ArrayList<String>) {
        picturesLiveData = ArrayList(picturePath.toSelectImageList())
    }
}

data class SelectedImage(val index: MutableLiveData<Int>, val path: MutableLiveData<String>) {
    companion object {
        fun create(index: Int, path: String): SelectedImage {
            return SelectedImage(MutableLiveData(index), MutableLiveData(path))
        }
    }
}

class GalleryViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    val mediaContentResolver = MediaContentResolver.newInstance(context)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GalleryViewModel(mediaContentResolver) as T
    }

}