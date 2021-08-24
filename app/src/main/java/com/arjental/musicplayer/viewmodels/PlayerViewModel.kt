package com.arjental.musicplayer.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjental.musicplayer.entitys.Track
import com.arjental.musicplayer.entitys.songs.Song
import com.arjental.musicplayer.repository.Repository
import com.arjental.musicplayer.services.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PlayerViewModel : ViewModel() {

    private val repository = Repository.get()

    private val _trackTitle = MutableLiveData<String>()
    val trackTitle: LiveData<String> = _trackTitle

    private val _trackArtists = MutableLiveData<String>()
    val trackArtists: LiveData<String> = _trackArtists

    private val _trackPreview = MutableLiveData<Bitmap>()
    val trackPreview: LiveData<Bitmap> = _trackPreview

    fun setCurrentTrack(track: Track) {
        _trackTitle.value = track.title
        _trackArtists.value = track.artist

        viewModelScope.launch(Dispatchers.Default) {
            val cover = track.downloadedTrackCover ?: repository.getPreview(track.coverUrl)
            withContext(Dispatchers.Main) { _trackPreview.value = cover }
        }

    }

    private var shouldSeekbarUpdates = true

    fun seekbarHaveToUpdates(bool: Boolean) {
        shouldSeekbarUpdates = bool
    }

    fun isSeekbarUpdates() = shouldSeekbarUpdates

    private val musicServiceConnection = MusicServiceConnection.get()

    val isConnected = musicServiceConnection.isConnected
    val networkError = musicServiceConnection.networkError
    val curPlayingSong = musicServiceConnection.curPlayingSong
    var curPlayingSongTitle: String? = null
    val playbackState = musicServiceConnection.playbackState

    private val _isPlaying = MutableLiveData<Boolean>()
    val isPlaying: LiveData<Boolean> = _isPlaying

    private val _curSongDuration = MutableLiveData<Long>()
    val curSongDuration: LiveData<Long> = _curSongDuration

    private val _curPlayerPosition = MutableLiveData<Long>()
    val curPlayerPosition: LiveData<Long> = _curPlayerPosition

    init {
        updateCurrentPlayerPosition()
    }

    fun playOrToggleSong(mediaItem: Song, toggle: Boolean = false) {
        if (curPlayingSongTitle != null) {
            if (curPlayingSongTitle == mediaItem.title) {
                playbackState.value?.let { playbackState ->
                    when {
                        playbackState.isPlaying -> if (toggle) {
                            musicServiceConnection.transportControls.pause()
                            _isPlaying.value = false
                        }
                        playbackState.isPlayEnabled -> {
                            musicServiceConnection.transportControls.play()
                            _isPlaying.value = true
                        }
                        else -> Unit
                    }
                }
            }
        } else {
            musicServiceConnection.transportControls.playFromMediaId(mediaItem.title, null)
            _isPlaying.value = true
            curPlayingSongTitle = mediaItem.title
        }
    }

    private fun updateCurrentPlayerPosition() {
        viewModelScope.launch {
            while (true) {
                val pos = playbackState.value?.currentPlaybackPosition
                if (curPlayerPosition.value != pos) {
                    _curPlayerPosition.postValue(pos!!)
                    _curSongDuration.postValue(MusicPlayerService.curSongDuration)
                }
                delay(200)
            }
        }
    }

    fun skipToNextSong() {
        musicServiceConnection.transportControls.skipToNext()
    }

    fun skipToPreviousSong() {
        musicServiceConnection.transportControls.skipToPrevious()
    }

    fun seekTo(pos: Long) {
        musicServiceConnection.transportControls.seekTo(pos)
    }



}