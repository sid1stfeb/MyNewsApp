package com.android.mynewsapp.di

import com.android.mynewsapp.data.network.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FakeAppModule{

    @Provides
    @Singleton
    fun provideApiHelper(): ApiHelper = FakeApiHelperImpl()
}