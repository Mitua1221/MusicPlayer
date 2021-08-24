package com.arjental.musicplayer.entitys.tracksresponse

import com.arjental.musicplayer.entitys.Artist
import com.arjental.musicplayer.entitys.Track

data class ApiTracksCollection(
    val track: Map<String, Track>,
    val people: Map<String, Artist>,
)
