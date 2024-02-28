package com.kursatmemis.artbook.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "art")
data class Art(
    @PrimaryKey
    val id: Int? = null,
    val artImage: ByteArray?,
    val artName: String?,
    val artistName: String?,
    val year: String?,
) : Serializable
