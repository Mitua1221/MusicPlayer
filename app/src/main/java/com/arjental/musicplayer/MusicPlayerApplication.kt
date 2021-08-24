package com.arjental.musicplayer

import android.app.Application
import com.arjental.musicplayer.repository.Repository
import com.arjental.musicplayer.services.InstanceHolder
import com.arjental.musicplayer.services.MusicServiceConnection

class MusicPlayerApplication: Application() {

    override fun onCreate() {
        Repository.initialize(this)
        MusicServiceConnection.initialize(this)
        InstanceHolder.initialize(this)
        super.onCreate()
    }



}