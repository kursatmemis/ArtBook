package com.kursatmemis.artbook.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ActivityContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ImageTransformer @Inject constructor(@ActivityContext val context: Context){

    fun convertToBitMap(imageUri: Uri?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            if (imageUri != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(context.contentResolver, imageUri)
                    bitmap = ImageDecoder.decodeBitmap(source)
                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }

    fun fromByteArrayToBitmap(byteArray: ByteArray): Bitmap? {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return bitmap
    }

    fun fromBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
        val byteArray = outputStream.toByteArray()
        return byteArray
    }

    fun makeSmallerBitmap(image: Bitmap, maximumSize : Int) : Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio : Double = width.toDouble() / height.toDouble()
        if (bitmapRatio > 1) {
            // landscape
            width = maximumSize
            val scaledHeight = width / bitmapRatio
            height = scaledHeight.toInt()
        } else {
            // portrait
            height = maximumSize
            val scaledWidth = height * bitmapRatio
            width = scaledWidth.toInt()
        }

        return Bitmap.createScaledBitmap(image,width,height,true)

    }
}