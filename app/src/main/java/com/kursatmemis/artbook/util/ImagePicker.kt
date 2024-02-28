package com.kursatmemis.artbook.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kursatmemis.artbook.model.GalleryPermissionState
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ImagePicker @Inject constructor(@ActivityContext private val context: Context) {

    fun selectImage(): GalleryPermissionState {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Sürümü 33'den büyük olan cihazlarda resim seç.
            return selectImageOnDevicesWithVersionGreaterThan33()
        } else {
            // Sürümü 33'den küçük olan cihazlarda resim seç.
            return selectImageOnDevicesWithVersionLessThan33()
        }
    }

    private fun selectImageOnDevicesWithVersionLessThan33(): GalleryPermissionState {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Izin verilmemiş.
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                // Izin isterken kullanıcıya bir mantık göstermeliyiz. (İznin neden istendiğini açıkla)
                return GalleryPermissionState.PERMISSION_DENIED_WITH_RATIONALE
            } else {
                // Izin isterken kullanıcıya bir mantık göstermemize gerek yok.
                return GalleryPermissionState.PERMISSION_DENIED_NO_RATIONALE
            }
        } else {
            // Izin verilmiş.
            return GalleryPermissionState.PERMISSION_GRANTED
        }
    }


    private fun selectImageOnDevicesWithVersionGreaterThan33(): GalleryPermissionState {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Izin verilmemiş.
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.READ_MEDIA_IMAGES
                )
            ) {
                // Izin istersen kullanıcıya bir mantık göstermeliyiz. (İznin neden istendiğini açıkla)
                return GalleryPermissionState.PERMISSION_DENIED_WITH_RATIONALE
            } else {
                // Izin istersen kullanıcıya bir mantık göstermemize gerek yok.
                return GalleryPermissionState.PERMISSION_DENIED_NO_RATIONALE
            }
        } else {
            // Izin verilmiş.
            return GalleryPermissionState.PERMISSION_GRANTED
        }
    }


}