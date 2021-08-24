package com.arjental.musicplayer.recyclerview.albumsrecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.arjental.musicplayer.ConstantsAndKeys.ALBUM_ID
import com.arjental.musicplayer.ConstantsAndKeys.DATA_FOR_TRACKS_LIST
import com.arjental.musicplayer.R
import com.arjental.musicplayer.databinding.AlbumsRecyclerItemBinding
import com.arjental.musicplayer.ui.fragments.TracksFragment
import com.arjental.musicplayer.viewmodels.AlbumsListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TAG = "MusicAdapter"

class AlbumsAdapter(
    private val fragmentManager: FragmentManager,
    private val viewModel: AlbumsListViewModel,
    private val scope: CoroutineScope
) :
    RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView.rootView) {

        val binding = AlbumsRecyclerItemBinding.bind(itemView)

        init {
            itemView.setOnClickListener {

                fragmentManager.setFragmentResult(
                    DATA_FOR_TRACKS_LIST,
                    bundleOf(
                        ALBUM_ID to viewModel.albumsList.value!![adapterPosition]
                    )
                )

                fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, TracksFragment())
                    .addToBackStack(null)
                    .commit()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.albums_recycler_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        with(holder) {
            binding.albumPreview.setImageResource(0)
            binding.albumTitle.text = viewModel.albumsList.value!![position].title
            binding.artistTitle.text = viewModel.albumsList.value!![position].artist
            scope.launch (Dispatchers.Default) {
                val preview = viewModel.loadPreview(viewModel.albumsList.value!![position])
                launch (Dispatchers.Main) {
                    if (binding.albumTitle.text == viewModel.albumsList.value!![position].title) {
                        binding.albumPreview.setImageBitmap(preview)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return viewModel.albumsList.value?.size ?: 0
    }

    fun setData() {
        this.notifyDataSetChanged()
    }
}