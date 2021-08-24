package com.arjental.musicplayer.entitys

import android.graphics.Bitmap
import android.net.Uri
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.net.URI

data class Album(
    val id: String,
    @SerializedName("name") val title: String,
    var downloadedAlbumCover: Bitmap? = null,
    var artist: String = "",
    val coverUrl: String,
    val peopleIds: List<String>,
): Serializable
