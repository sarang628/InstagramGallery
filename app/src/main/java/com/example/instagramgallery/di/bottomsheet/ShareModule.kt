package com.sarang.torangbottomsheet.di.bottomsheet

import com.sarang.torang.usecase.GetFollowerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class ShareModule {
    @Provides
    fun providesGetFollowerUseCase(

    ): GetFollowerUseCase {
        return object : GetFollowerUseCase {
            override suspend fun invoke(): List<com.sarang.torang.data.bottomsheet.User> {
                return listOf()
            }
        }
    }

}