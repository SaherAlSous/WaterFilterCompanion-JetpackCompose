package com.saher.android.waterfiltercompanion_jetpackcompose.common.dependencyinjection.application

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.saher.android.waterfiltercompanion_jetpackcompose.Application
import com.saher.android.waterfiltercompanion_jetpackcompose.common.dependencyinjection.DiConstants
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Named(DiConstants.APPLICATION_CONTEXT)
    fun provideApplicationContext(application: Application): Context = application

}