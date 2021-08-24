package com.arjental.musicplayer.repository

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import com.arjental.musicplayer.R
import com.arjental.musicplayer.entitys.Album
import com.arjental.musicplayer.entitys.Track
import com.arjental.musicplayer.network.GlideImageDownloader
import com.arjental.musicplayer.network.RetrofitInstance

class Repository(val context: Context) {

    private val retrofit = RetrofitInstance()
    private val glide = GlideImageDownloader(context)
    private val converter = Converter()

    suspend fun getAlbums(): List<Album>? {
        return try {
            val response = retrofit.api.getAlbums(method = GET_ALBUM_LIST_TAG)
            if (response.isSuccessful && response.body()?.error?.code == 0 && response.body() != null) {
                converter.convertToAlbumListFromResponse(response)
            } else {
                null
            }
        } catch (e: Exception) {
            Toast.makeText(context, R.string.connection_exception, Toast.LENGTH_SHORT).show()
            null
        }
    }

    suspend fun getAlbumTracks(id: String): List<Track>? {
        return try {
            val response = retrofit.api.getTracks(method = GET_ALBUM_CARD_TAG, id = id)
            if (response.isSuccessful && response.body()?.error?.code == 0 && response.body() != null) {
                converter.convertToTrackListFromResponse(response)
            } else {
                null
            }
        } catch (e: Exception) {
            Toast.makeText(context, R.string.connection_exception, Toast.LENGTH_SHORT).show()
            null
        }
    }

    suspend fun getPreview(url: String): Bitmap = glide.downloadPreview(url)

    companion object {
        const val GET_ALBUM_LIST_TAG = "product.getNews"

        const val GET_ALBUM_CARD_TAG = "product.getCard"

        @SuppressLint("StaticFieldLeak")//Its Application context, its not changing while appl running
        private var INSTANCE: Repository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = Repository(context = context)
            }
        }

        fun get(): Repository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized")
        }

    }

}