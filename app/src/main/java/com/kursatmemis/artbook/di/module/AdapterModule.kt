package com.kursatmemis.artbook.di.module

import android.content.Context
import com.kursatmemis.artbook.adapter.ArtAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class AdapterModule {

    @Provides
    @FragmentScoped
    fun provideArtAdapter(@ActivityContext context: Context) : ArtAdapter {
        return ArtAdapter(context, ArrayList())
    }

}