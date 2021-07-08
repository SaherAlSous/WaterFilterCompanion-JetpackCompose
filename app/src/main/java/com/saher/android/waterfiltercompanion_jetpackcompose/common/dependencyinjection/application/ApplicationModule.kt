package com.saher.android.waterfiltercompanion_jetpackcompose.common.dependencyinjection.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.saher.android.waterfiltercompanion_jetpackcompose.common.date.DateHelper
import com.saher.android.waterfiltercompanion_jetpackcompose.common.dependencyinjection.DiConstants
import com.saher.android.waterfiltercompanion_jetpackcompose.datapresistance.LocalRespository
import dagger.Reusable
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Named(DiConstants.APPLICATION_CONTEXT)
    fun provideApplicationContext(application: Application): Context = application

    @Provides
    @Reusable
    fun provideDateHelper() = DateHelper()

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @Named(DiConstants.APPLICATION_CONTEXT) context: Context
    ): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context) //androidx.preference:preference-ktx:1.1.1

    @Provides
    @Reusable
    fun provideLocalRepository(
        sharedPreferences: SharedPreferences
    ): LocalRespository= LocalRespository(sharedPreferences)

}