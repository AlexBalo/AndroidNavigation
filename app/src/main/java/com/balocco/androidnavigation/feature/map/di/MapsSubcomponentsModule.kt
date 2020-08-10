package com.balocco.androidnavigation.feature.map.di

import com.balocco.androidnavigation.data.local.UserLocationDataSource
import com.balocco.androidnavigation.di.ActivityScope
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides

@Module
class MapsSubcomponentsModule {

    @Provides
    @ActivityScope
    fun provideUserLocationProvider(
        fusedLocationProviderClient: FusedLocationProviderClient
    ): UserLocationDataSource =
        UserLocationDataSource(
            fusedLocationProviderClient
        )

}