package com.kursatmemis.artbook.util

import android.content.Context
import android.widget.Toast

fun showToastMessage(context: Context, message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, message, duration).show()
}

