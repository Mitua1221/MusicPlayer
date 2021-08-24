package com.arjental.musicplayer.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.arjental.musicplayer.entitys.Album
import com.arjental.musicplayer.repository.Repository
import kotlinx.coroutines.launch

class AlbumsListViewModel : ViewModel() {

    private val repository = Repository.get()

    private val _albumsList = MutableLiveData<List<Album>>()
    val albumsList: LiveData<List<Album>> = _albumsList

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _albumsList.value = repository.getAlbums() ?: emptyList()
        }
    }

    suspend fun loadPreview(album: Album): Bitmap {

        val preview = repository.getPreview(album.coverUrl)

        _albumsList.value?.forEach {
            if (it.id == album.id && it.artist == album.artist) it.downloadedAlbumCover = preview

        }

        return preview

    }


}