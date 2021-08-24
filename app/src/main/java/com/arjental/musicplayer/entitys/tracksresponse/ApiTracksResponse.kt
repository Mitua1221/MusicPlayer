package com.arjental.musicplayer.entitys.tracksresponse

import com.arjental.musicplayer.entitys.ApiError

data class ApiTracksResponse (
    val error: ApiError,
    val collection: ApiTracksCollection,
)