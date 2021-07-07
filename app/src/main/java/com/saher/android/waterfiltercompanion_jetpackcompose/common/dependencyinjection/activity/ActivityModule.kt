package com.saher.android.waterfiltercompanion_jetpackcompose.common.dependencyinjection.activity

import android.app.Activity
import android.content.Context
import com.saher.android.waterfiltercompanion_jetpackcompose.common.dependencyinjection.DiConstants
import com.saher.android.waterfiltercompanion_jetpackcompose.common.dialog.MaterialDialogHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    @Named(DiConstants.ACTIVITY_CONTEXT)
    fun provideActivityContext(activity: Activity) :Context = activity

    @Provides
    fun provideMaterialDialogHelper(
        @Named(DiConstants.ACTIVITY_CONTEXT) context: Context
    ) = MaterialDialogHelper(context)

}