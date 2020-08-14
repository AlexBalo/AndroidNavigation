package com.balocco.androidnavigation.feature.map.di

import android.app.Activity
import com.balocco.androidnavigation.di.ActivityScope
import com.balocco.androidnavigation.feature.detail.di.DetailComponent
import com.balocco.androidnavigation.feature.map.ui.MapsActivity
import com.balocco.androidnavigation.feature.venues.di.VenuesComponent
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        MapModule::class,
        MapsSubcomponentsModule::class,
        MapsViewModelModule::class]
)
interface MapsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: Activity
        ): MapsComponent
    }

    fun inject(activity: MapsActivity)

    fun venuesComponent(): VenuesComponent.Factory
    fun detailComponent(): DetailComponent.Factory
}