package com.arjental.musicplayer.repository

import com.arjental.musicplayer.entitys.Album
import com.arjental.musicplayer.entitys.ApiAlbumResponse
import com.arjental.musicplayer.entitys.Track
import com.arjental.musicplayer.entitys.tracksresponse.ApiTracksResponse
import retrofit2.Response

class Converter {

    fun convertToAlbumListFromResponse(response: Response<ApiAlbumResponse>): List<Album> {
        val artists = response.body()!!.collection.people
        val albums = response.body()!!.collection.album.values.toList()
        albums.forEach { album ->
            album.peopleIds.forEachIndexed { index, artist ->
                album.artist = if (index == 0) {
                    artists[artist]?.name ?: ""
                } else {
                    if (album.artist == "") {
                        album.artist + artists[artist]?.name ?: ""
                    } else {
                        album.artist + ", " + artists[artist]?.name ?: ""
                    }
                }

            }
        }
        return albums
    }

    fun convertToTrackListFromResponse(response: Response<ApiTracksResponse>): List<Track> {

        val artists = response.body()!!.collection.people
        val tracks = response.body()!!.collection.track.values.toList()
        tracks.forEach { track ->
            track.peopleIds.forEachIndexed { index, artist ->
                track.artist = if (index == 0) {
                    artists[artist]?.name ?: ""
                } else {
                    if (track.artist == "") {
                        track.artist + artists[artist]?.name ?: ""
                    } else {
                        track.artist + ", " + artists[artist]?.name ?: ""
                    }
                }

            }
        }
        return tracks

    }




}