package com.kursatmemis.artbook.di.module

import android.content.Context
import androidx.room.Room
import com.kursatmemis.artbook.room.AppDatabase
import com.kursatmemis.artbook.room.ArtDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArtDao(appDatabase: AppDatabase) : ArtDao {
        return appDatabase.artDao()
    }

}