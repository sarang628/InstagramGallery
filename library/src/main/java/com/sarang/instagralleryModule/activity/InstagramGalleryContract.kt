package com.sarang.instagralleryModule.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper

class InstagramGalleryContract : ActivityResultContract<String?, Intent?>() {
    @CallSuper
    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, GalleryActivity::class.java)
    }

    fun getSynchronousResult(
        context: Context,
        input: String
    ): SynchronousResult<Uri>? {
        return null
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Intent? {
        Log.d("__sarang", ""+resultCode)
        return if (intent == null || resultCode != Activity.RESULT_OK) null else intent
    }
}