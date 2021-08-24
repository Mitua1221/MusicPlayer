package com.arjental.musicplayer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arjental.musicplayer.R
import com.arjental.musicplayer.ui.fragments.AlbumsFragment

class MusicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.music_activity)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = AlbumsFragment()
            supportFragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}