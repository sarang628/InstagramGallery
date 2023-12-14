package com.example.instagramgallery.di.gallery

import android.content.Context
import com.example.mediacontentresolverlibrary.MediaContentResolver
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
}