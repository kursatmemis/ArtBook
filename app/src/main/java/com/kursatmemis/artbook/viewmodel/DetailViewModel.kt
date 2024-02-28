package com.kursatmemis.artbook.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursatmemis.artbook.model.Art
import com.kursatmemis.artbook.room.ArtDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val artDao: ArtDao
) : ViewModel() {

    private val _savingOperationResult = MutableLiveData<Boolean>()
    val savingOperationResult get() = _savingOperationResult

    fun saveArt(art: Art) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    artDao.insertArt(art)
                }
                _savingOperationResult.value = true
            } catch (e: Exception) {
                Log.w("mKm - exception", e.message.toString())
                _savingOperationResult.value = false
            }
        }
    }

}