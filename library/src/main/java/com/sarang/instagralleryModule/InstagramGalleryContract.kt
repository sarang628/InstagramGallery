package com.sarang.instagralleryModule

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper

/**
 * An [ActivityResultContract] to prompt the user to pick a piece of content, receiving
 * a `content://` [Uri] for that content that allows you to use
 * [android.content.ContentResolver.openInputStream] to access the raw data. By
 * default, this adds [Intent.CATEGORY_OPENABLE] to only return content that can be
 * represented as a stream.
 *
 *
 * The input is the mime type to filter by, e.g. `image/ *`.
 *
 *
 * This can be extended to override [.createIntent] if you wish to pass additional
 * extras to the Intent created by `super.createIntent()`.
 */
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