package com.example.instagramgallery.di.gallery

import android.content.Context
import com.example.mediacontentresolverlibrary.MediaContentResolver
import com.sarang.instagralleryModule.usecase.GetFolderListUseCase
import com.sarang.instagralleryModule.usecase.GetPictureListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class MediaContentResolverModule {
    @Provides
    fun ProvideMediaContentResolver(@ApplicationContext context: Context): MediaContentResolver {
        return MediaContentResolver.newInstance(context = context)
    }

    @Provides
    fun ProvideGetFolderListUseCase(mediaContentResolver: MediaContentResolver): GetFolderListUseCase {
        return object : GetFolderListUseCase {
            override fun invoke(): List<String> {
                return mediaContentResolver.getFolderList()
            }
        }
    }

    @Provides
    fun ProvideGetPictureListUseCase(mediaContentResolver: MediaContentResolver): GetPictureListUseCase {
        return object : GetPictureListUseCase {
            override fun invoke(folderName: String): List<String> {
                return mediaContentResolver.getPictureList()
            }
        }
    }
}