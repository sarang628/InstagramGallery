package com.example.instagramgallery

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mediacontentresolverlibrary.ImageData
import com.example.mediacontentresolverlibrary.MediaContentResolver

class GalleryViewModel(val mediaContentResolver: MediaContentResolver) : ViewModel() {
    private val _isMultiSelect = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isMultiSelect: LiveData<Boolean> = _isMultiSelect

    private val _currentSelectedImage = MutableLiveData<String>()
    val currentSelectedImage: LiveData<String> = _currentSelectedImage


    fun selectImage(path: String) {
        _currentSelectedImage.value = path
    }


    private val selectedPictures = ArrayList<SelectedImage>()

    fun clickMultiSelect() {
        _isMultiSelect.value = !_isMultiSelect.value!!
    }

    fun selectPicture(imageData: ImageData) {
        selectedPictures.add(SelectedImage.create(selectedPictures.size, imageData))
    }
}

data class SelectedImage(val index: MutableLiveData<Int>, val path: MutableLiveData<ImageData>) {
    companion object {
        fun create(index: Int, imageData: ImageData): SelectedImage {
            return SelectedImage(MutableLiveData(index), MutableLiveData(imageData))
        }
    }
}

class GalleryViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    val mediaContentResolver = MediaContentResolver.newInstance(context)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GalleryViewModel(mediaContentResolver) as T
    }

}