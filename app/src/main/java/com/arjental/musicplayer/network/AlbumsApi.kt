package com.arjental.musicplayer.network

import com.arjental.musicplayer.entitys.ApiAlbumResponse
import com.arjental.musicplayer.entitys.tracksresponse.ApiTracksResponse
import retrofit2.Response
import retrofit2.http.*

interface AlbumsApi {

        @GET(".")
        suspend fun getAlbums(
                @Query("method") method: String
        ): Response<ApiAlbumResponse>

        @GET(".")
        suspend fun getTracks(
                @Query("method") method: String,
                @Query("productId") id: String,
        ): Response<ApiTracksResponse>

}