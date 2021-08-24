package com.arjental.musicplayer.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arjental.musicplayer.ConstantsAndKeys
import com.arjental.musicplayer.ConstantsAndKeys.DATA_FOR_PLAYER
import com.arjental.musicplayer.ConstantsAndKeys.TRACK_ID
import com.arjental.musicplayer.R
import com.arjental.musicplayer.databinding.PlayerFragmentBinding
import com.arjental.musicplayer.entitys.Track
import com.arjental.musicplayer.services.MusicPlayerService
import com.arjental.musicplayer.viewmodels.PlayerViewModel

private const val TAG = "PlayerFragment"

class PlayerFragment : Fragment(R.layout.player_fragment) {

    private var _binding: PlayerFragmentBinding? = null
    private val binding get() = _binding!!

    private val playerViewModel: PlayerViewModel by lazy {
        ViewModelProvider(this).get(PlayerViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().startService(Intent(requireContext(), MusicPlayerService::class.java))

        requireActivity().supportFragmentManager.setFragmentResultListener(
            DATA_FOR_PLAYER,
            this
        ) { _, bundle ->
            val track = bundle.get(TRACK_ID)
            if (track == null) throw IllegalArgumentException("Empty ID came to $TAG") else {
                playerViewModel.setCurrentTrack(track as Track)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlayerFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerViewModel.trackTitle.observe(viewLifecycleOwner) {
            binding.albumTitle.text = it
        }

        playerViewModel.trackArtists.observe(viewLifecycleOwner) {
            binding.artists.text = it
        }

        playerViewModel.trackPreview.observe(viewLifecycleOwner) {
            binding.albumPreview.setImageBitmap(it)
        }

        playerViewModel.curSongDuration.observe(viewLifecycleOwner) {
            binding.progressBar.max = it.toInt()
        }

        playerViewModel.curPlayerPosition.observe(viewLifecycleOwner) {
            if(playerViewModel.isSeekbarUpdates()) {
                binding.progressBar.progress = it.toInt()
            }
        }

        playerViewModel.isPlaying.observe(viewLifecycleOwner) {
            if(it) {
                binding.controlButton.setImageResource(android.R.drawable.ic_media_pause)
            } else {
                binding.controlButton.setImageResource(android.R.drawable.ic_media_play)
            }
        }

        binding.controlButton.setOnClickListener {
            playerViewModel.playOrToggleSong(ConstantsAndKeys.firstSong, true)
        }

        binding.nextButton.setOnClickListener {
            playerViewModel.skipToNextSong()
        }

        binding.prevButton.setOnClickListener {
            playerViewModel.skipToPreviousSong()
        }

        binding.progressBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) = Unit

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                playerViewModel.seekbarHaveToUpdates(false)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    playerViewModel.seekTo(it.progress.toLong())
                    playerViewModel.seekbarHaveToUpdates(true)
                }
            }
        })



    }

    override fun onDestroy() {
        super.onDestroy()
        playerViewModel.curPlayingSongTitle = null
        //requireActivity().stopService(Intent(requireContext(), MusicPlayerService::class.java))
    }

}