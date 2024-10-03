package com.sarang.torangbottomsheet.di.bottomsheet

import android.util.Log
import com.sarang.torang.data.dao.LoggedInUserDao
import com.sarang.torang.data.dao.ReviewDao
import com.sarang.torang.usecase.IsMyReviewUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MenuModule {
    @Provides
    fun ProvidesGetReviewUseCase(

    ): IsMyReviewUseCase {
        return object : IsMyReviewUseCase {
            override suspend fun invoke(reviewId: Int): Boolean {
                return false
            }
        }
    }
}