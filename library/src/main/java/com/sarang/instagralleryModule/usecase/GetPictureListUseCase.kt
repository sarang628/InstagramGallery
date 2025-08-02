package com.sarang.instagralleryModule.usecase

interface GetPictureListUseCase {
    fun invoke(folderName: String = ""): List<String>
}