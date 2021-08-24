package com.arjental.musicplayer.entitys.albumsresponse

import com.arjental.musicplayer.entitys.Album
import com.arjental.musicplayer.entitys.Artist

data class ApiAlbumsCollection(
    val album: Map<String, Album>,
    val people: Map<String, Artist>,
)

