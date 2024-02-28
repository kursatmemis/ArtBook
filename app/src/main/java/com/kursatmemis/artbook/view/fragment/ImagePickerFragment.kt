package com.kursatmemis.artbook.view.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.kursatmemis.artbook.model.GalleryPermissionState
import com.kursatmemis.artbook.util.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import java.security.Permission
import javax.inject.Inject

abstract class ImagePickerFragment<T: ViewBinding> : BaseFragment<T>() {

    @Inject
    lateinit var imagePicker: ImagePicker

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    var imageURI: Uri? = null

    abstract fun getImageView(): ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLauncher()
    }

    private fun registerLauncher() {

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result->
            if (result.resultCode == RESULT_OK) {
                val intentFromResult = result.data
                intentFromResult?.let {
                    val selectedImageUri = it.data
                    imageURI = selectedImageUri
                    val imageView = getImageView()
                    imageView.setImageURI(imageURI)
                }
            }
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {result->
            if (result) {
                // Izin verilmiş.
                val intentToGallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            } else {
                // Izin verilmemiş.
                Toast.makeText(
                    requireContext(),
                    "You have to give a permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    fun startImageSelectionProcess() {
        val galleryPermissionState = imagePicker.selectImage()
        when (galleryPermissionState) {
            GalleryPermissionState.PERMISSION_GRANTED -> {
                val intentToGallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }

            GalleryPermissionState.PERMISSION_DENIED_WITH_RATIONALE -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Snackbar.make(
                        binding.root,
                        "You have to give a permission to select an image from your gallery.",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Give Permission") {
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }.show()
                } else {
                    Snackbar.make(
                        binding.root,
                        "You have to give a permission to select an image from your gallery.",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Give Permission") {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }.show()
                }

            }

            GalleryPermissionState.PERMISSION_DENIED_NO_RATIONALE -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    }

}