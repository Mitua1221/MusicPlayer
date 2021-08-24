package com.arjental.musicplayer.services.callbacks

import android.widget.Toast
import com.arjental.musicplayer.services.MusicPlayerService
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player

class MusicPlayerEventListener(private val musicPlayerService: MusicPlayerService) : Player.EventListener {

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)
        if (playbackState == Player.STATE_READY && !playWhenReady) {
            musicPlayerService.stopForeground(false)
        }
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        super.onPlayerError(error)
        Toast.makeText(musicPlayerService, "An unknown error occurred", Toast.LENGTH_LONG).show()
    }
}