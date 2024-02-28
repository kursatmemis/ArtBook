package com.kursatmemis.artbook.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kursatmemis.artbook.databinding.FragmentDetailBinding
import com.kursatmemis.artbook.model.Art
import com.kursatmemis.artbook.util.ImageTransformer
import com.kursatmemis.artbook.util.showToastMessage
import com.kursatmemis.artbook.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : ImagePickerFragment<FragmentDetailBinding>() {

    @Inject
    lateinit var imageTransformer: ImageTransformer
    private val detailViewModel: DetailViewModel by viewModels()
    private var art: Art? = null
    private var showArtDetail: Boolean = false

    override fun getImageView(): ImageView {
        return binding.artImageImageView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle: DetailFragmentArgs by navArgs()
        art = bundle.art
        showArtDetail = bundle.showArtDetail
    }

    override fun createBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, false)
    }

    override fun setupUI() {

        if (showArtDetail) {
          showArtDetailOnViews()
        }

        setupImageViewClickListener()
        setupSaveButtonClickListener()
        observeLiveData()
    }

    private fun showArtDetailOnViews() {
        val artImage = art?.artImage
        val artName = art?.artName
        val artistName = art?.artistName
        val year = art?.year

        if (artImage != null) {
            val bitmap = imageTransformer.fromByteArrayToBitmap(artImage)
            binding.artImageImageView.setImageBitmap(bitmap)
        }
        binding.artNameEditText.setText(artName)
        binding.artistNameEditText.setText(artistName)
        binding.artYearEditText.setText(year)
    }

    private fun setupImageViewClickListener() {
        binding.artImageImageView.setOnClickListener {
            startImageSelectionProcess()
        }
    }

    private fun setupSaveButtonClickListener() {
        binding.saveButton.setOnClickListener {
            startArtSavingProcess()
        }
    }

    private fun startArtSavingProcess() {
        val art = getArt()
        detailViewModel.saveArt(art)
    }

    private fun observeLiveData() {
        detailViewModel.savingOperationResult.observe(viewLifecycleOwner) {savingOperationResult ->
            val feedbackMessage: String = if (savingOperationResult) {
                "The Art saved!"
            } else {
                "The Art could not be saved due to an unknown error!"
            }
            showToastMessage(requireContext(), feedbackMessage)
        }
    }

    private fun getArt(): Art {
        val artImage = getImageAsByteArray()
        val artName = binding.artNameEditText.text.toString()
        val artistName = binding.artistNameEditText.text.toString()
        val year = binding.artYearEditText.text.toString()
        return Art(artImage = artImage, artName = artName, artistName = artistName, year = year)
    }

    private fun getImageAsByteArray(): ByteArray? {
        val bitmap = imageTransformer.convertToBitMap(imageURI)
        val smallerBitmap = bitmap?.let { imageTransformer.makeSmallerBitmap(it, 300) }
        val byteArray = smallerBitmap?.let { imageTransformer.fromBitmapToByteArray(it) }
        return byteArray
    }

}