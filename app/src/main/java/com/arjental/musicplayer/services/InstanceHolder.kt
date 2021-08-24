package com.arjental.musicplayer.services

import android.content.Context
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class InstanceHolder (context: Context) {

    val exoPlayer = SimpleExoPlayer.Builder(context).build().apply {
        setAudioAttributes(audioAttributes, true)
        setHandleAudioBecomingNoisy(true)
    }

    var dataSourceFactory: DefaultDataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "MusicPlayer"))


    companion object {

        private var INSTANCE: InstanceHolder? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = InstanceHolder(context)
            }
        }

        fun get(): InstanceHolder {
            return INSTANCE ?: throw IllegalStateException("InstanceHolder must be initialized")
        }

    }

}