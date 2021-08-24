package com.arjental.musicplayer.entitys

import android.graphics.Bitmap
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Track(
    val id: String,
    @SerializedName("name") val title: String,
    var downloadedTrackCover: Bitmap? = null,
    var artist: String = "",
    val coverUrl: String,
    val peopleIds: List<String>,
): Serializable

