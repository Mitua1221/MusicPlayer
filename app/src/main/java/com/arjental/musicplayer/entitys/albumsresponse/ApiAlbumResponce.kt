package com.arjental.musicplayer.entitys

import com.arjental.musicplayer.entitys.albumsresponse.ApiAlbumsCollection

data class ApiAlbumResponse(
    val error: ApiError,
    val collection: ApiAlbumsCollection,
)
