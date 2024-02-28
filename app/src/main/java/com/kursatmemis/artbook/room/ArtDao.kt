package com.kursatmemis.artbook.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kursatmemis.artbook.model.Art

@Dao
interface ArtDao {

    @Insert
    fun insertArt(art: Art)

    @Query("Select * from art")
    fun getArtList(): List<Art>

}