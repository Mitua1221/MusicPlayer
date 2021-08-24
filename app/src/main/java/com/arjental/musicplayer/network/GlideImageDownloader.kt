package com.arjental.musicplayer.network

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.arjental.musicplayer.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.delay

class GlideImageDownloader(val context: Context) {

    suspend fun downloadPreview(url: String) = download(url)

    private suspend fun download(url: String): Bitmap {

        var bitmap: Bitmap? = null

        Glide.with(context)
            .asBitmap()
            .placeholder(R.drawable.ic_launcher_foreground)
            .load(url)
            .into(object : CustomTarget<Bitmap>(200, 200) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                  bitmap = placeholder?.toBitmap(200, 200)
                }
            })

        while (bitmap == null) delay(50)

        return bitmap!!
    }
}