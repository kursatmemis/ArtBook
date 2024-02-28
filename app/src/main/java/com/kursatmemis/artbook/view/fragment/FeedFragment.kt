package com.kursatmemis.artbook.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.kursatmemis.artbook.adapter.ArtAdapter
import com.kursatmemis.artbook.databinding.FragmentFeedBinding
import com.kursatmemis.artbook.viewmodel.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FeedFragment : BaseFragment<FragmentFeedBinding>() {

    @Inject
    lateinit var artAdapter: ArtAdapter

    private val feedViewModel: FeedViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        feedViewModel.fetchArtList()
    }

    override fun createBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFeedBinding {
        return FragmentFeedBinding.inflate(inflater, container, false)
    }

    override fun setupUI() {
        setupArtListViewAdapter()
        setupClickListenerOnListViewItem()
        observeLiveData()
    }

    private fun setupArtListViewAdapter() {
        binding.artListView.adapter = artAdapter
    }

    private fun setupClickListenerOnListViewItem() {
        binding.artListView.setOnItemClickListener { parent, view, position, id ->
            val art = artAdapter.getArt(position)
            val navDirection = FeedFragmentDirections.actionFeedFragmentToDetailFragment(art, true)
            Navigation.findNavController(binding.root).navigate(navDirection)
        }
    }

    private fun observeLiveData() {
        feedViewModel.artList.observe(viewLifecycleOwner) {
            val newArtList = it
            artAdapter.updateAdapter(newArtList)
        }
    }

}