package com.balocco.androidnavigation.feature.map.di

import android.app.Activity
import com.balocco.androidnavigation.di.ActivityScope
import com.balocco.androidnavigation.feature.map.ui.MapsActivity
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = []
)
interface MapsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: Activity
        ): MapsComponent
    }

    fun inject(activity: MapsActivity)
}