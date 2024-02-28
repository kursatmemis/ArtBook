package com.kursatmemis.artbook.viewmodel

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
class FeedViewModel @Inject constructor(
    private val artDao: ArtDao
) : ViewModel() {

    private val _artList = MutableLiveData<ArrayList<Art>>()
    val artList get() = _artList

    fun fetchArtList() {
        viewModelScope.launch {
            val newArrayList = ArrayList<Art>()
            val artList = withContext(Dispatchers.IO) {
                artDao.getArtList()
            }
            newArrayList.addAll(artList)
            _artList.value = newArrayList
        }
    }

}