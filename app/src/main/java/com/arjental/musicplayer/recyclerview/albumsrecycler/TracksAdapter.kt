package com.arjental.musicplayer.recyclerview.albumsrecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.arjental.musicplayer.ConstantsAndKeys
import com.arjental.musicplayer.R
import com.arjental.musicplayer.databinding.AlbumsRecyclerItemBinding
import com.arjental.musicplayer.ui.fragments.PlayerFragment
import com.arjental.musicplayer.viewmodels.TracksListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TracksAdapter(
    val fragmentManager: FragmentManager,
    val viewModel: TracksListViewModel,
    private val scope: CoroutineScope,
) :
    RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {

    inner class TracksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = AlbumsRecyclerItemBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                fragmentManager.setFragmentResult(
                    ConstantsAndKeys.DATA_FOR_PLAYER,
                    bundleOf(
                        ConstantsAndKeys.TRACK_ID to viewModel.tracksList.value!![adapterPosition]
                    )
                )

                fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PlayerFragment())
                .addToBackStack(null)
                .commit()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        return TracksViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.albums_recycler_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {

        with(holder) {

            binding.albumTitle.text = viewModel.tracksList.value!![position].title
            binding.artistTitle.text = viewModel.tracksList.value!![position].artist

            if (viewModel.tracksList.value!![position].downloadedTrackCover == null) {
                scope.launch (Dispatchers.Default) {
                    val preview = viewModel.loadPreview()
                    launch (Dispatchers.Main) {
                        binding.albumPreview.setImageBitmap(preview)
                    }
                }
            } else {
                binding.albumPreview.setImageBitmap(viewModel.tracksList.value!![position].downloadedTrackCover)
            }

        }
    }

    override fun getItemCount(): Int {
        return viewModel.tracksList.value?.size ?: 0
    }

    fun setData() {
        this.notifyDataSetChanged()
    }
}