package com.arjental.musicplayer.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjental.musicplayer.entitys.Album
import com.arjental.musicplayer.entitys.Track
import com.arjental.musicplayer.repository.Repository
import kotlinx.coroutines.launch

class TracksListViewModel : ViewModel() {

    private val repository = Repository.get()

    private val _album = MutableLiveData<Album>()
    private val album: LiveData<Album> = _album

    private val _tracksList = MutableLiveData<List<Track>>()
    val tracksList: LiveData<List<Track>> = _tracksList

    fun loadTracks() {
        viewModelScope.launch {
            _tracksList.value = repository.getAlbumTracks(album.value!!.id) ?: emptyList()
        }
    }

    fun setReceivedAlbum(album: Album) {
        _album.value = album
        loadTracks()
    }

    suspend fun loadPreview(): Bitmap {

        val preview = repository.getPreview(album.value!!.coverUrl)

        _tracksList.value?.forEach {
            it.downloadedTrackCover = preview
        }

        return preview

    }

}