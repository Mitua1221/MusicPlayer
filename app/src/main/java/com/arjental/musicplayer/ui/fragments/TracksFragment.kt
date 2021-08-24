package com.arjental.musicplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arjental.musicplayer.ConstantsAndKeys.ALBUM_ID
import com.arjental.musicplayer.ConstantsAndKeys.DATA_FOR_TRACKS_LIST
import com.arjental.musicplayer.R
import com.arjental.musicplayer.databinding.TracksFragmentBinding
import com.arjental.musicplayer.entitys.Album
import com.arjental.musicplayer.recyclerview.albumsrecycler.TracksAdapter
import com.arjental.musicplayer.viewmodels.TracksListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import java.lang.IllegalArgumentException

private const val TAG = "TracksFragment"

class TracksFragment : Fragment(R.layout.tracks_fragment) {

    private var _binding: TracksFragmentBinding? = null
    private val binding get() = _binding!!

    private val tracksFragmentScope = CoroutineScope(
        Dispatchers.IO
    )

    private val tracksListViewModel: TracksListViewModel by lazy {
        ViewModelProvider(this).get(TracksListViewModel::class.java)
    }

    private val tracksAdapter by lazy {
        TracksAdapter(
            requireActivity().supportFragmentManager,
            tracksListViewModel,
            tracksFragmentScope,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().supportFragmentManager.setFragmentResultListener(
            DATA_FOR_TRACKS_LIST,
            this
        ) { _, bundle ->
            val album = bundle.get(ALBUM_ID)
            if (album == null) throw IllegalArgumentException("Empty ID came to $TAG") else {
                tracksListViewModel.setReceivedAlbum(album as Album)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TracksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        tracksListViewModel.tracksList.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                binding.trackLoadingProgressBar.visibility = View.GONE
                tracksAdapter.setData()
            } else {
                tracksListViewModel.loadTracks()
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.tracksRecyclerView.adapter = tracksAdapter
        binding.tracksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        tracksFragmentScope.cancel()
    }

}