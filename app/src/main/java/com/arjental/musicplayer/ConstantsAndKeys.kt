package com.arjental.musicplayer

import com.arjental.musicplayer.entitys.songs.Song

object ConstantsAndKeys {

    const val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
    const val NOTIFICATION_ID = 132

    private const val MY_MEDIA_ROOT_ID = "media_root_id"
    private const val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"

    const val MEDIA_ROOT_ID = "root_id"

    val firstSong = Song("First", "https://enazadevkz.cdnvideo.ru/tank3/medialand/2021_05_12/1.mp3")
    val secondSong = Song("Second", "https://enazadevkz.cdnvideo.ru/tank1/sony/A10301A0004574902L_20210331042345765/resources/ad5a61f35b99.mp3")

    const val DATA_FOR_TRACKS_LIST = "DataForTrackList"
    const val DATA_FOR_PLAYER = "DataForPlayer"

    const val ALBUM_ID = "AlbumId"
    const val TRACK_ID = "TrackId"

    const val FROM_TRACK_LIST_TO_PLAYER_BITMAP = "FromTrackListTolPlayerBitmap"

}