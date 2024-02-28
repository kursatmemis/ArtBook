package com.kursatmemis.artbook.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kursatmemis.artbook.model.Art

@Database(entities = [Art::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun artDao(): ArtDao

}