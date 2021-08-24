package com.arjental.musicplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arjental.musicplayer.R
import com.arjental.musicplayer.databinding.AlbumsFragmentBinding
import com.arjental.musicplayer.recyclerview.albumsrecycler.AlbumsAdapter
import com.arjental.musicplayer.viewmodels.AlbumsListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class AlbumsFragment : Fragment(R.layout.albums_fragment) {

    private var _binding: AlbumsFragmentBinding? = null
    private val binding get() = _binding!!

    private val albumsListViewModel: AlbumsListViewModel by lazy {
        ViewModelProvider(this).get(AlbumsListViewModel::class.java)
    }

    private val albumsFragmentScope = CoroutineScope(
        Dispatchers.IO
    )

    private val albumAdapter by lazy {
        AlbumsAdapter(
            requireActivity().supportFragmentManager,
            albumsListViewModel,
            albumsFragmentScope
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlbumsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        albumsListViewModel.albumsList.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                binding.albumLoadingProgressBar.visibility = View.GONE
                albumAdapter.setData()
            } else {
                albumsListViewModel.load()
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.albumsRecyclerView.adapter = albumAdapter
        binding.albumsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        albumsFragmentScope.cancel()
    }

}