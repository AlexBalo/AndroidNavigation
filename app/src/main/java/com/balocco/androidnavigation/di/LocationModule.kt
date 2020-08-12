package com.balocco.androidnavigation.di

import android.content.Context
import com.balocco.androidcomponents.di.ApplicationScope
import com.balocco.androidnavigation.data.local.UserLocationLocalDataSource
import com.balocco.androidnavigation.data.local.UserLocationLocalDataSourceImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides

/* Module that contains dependencies for location. */
@Module
class LocationModule {

    @Provides
    @ApplicationScope
    fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @ApplicationScope
    fun provideUserLocationLocalDataSource(
        fusedLocationProviderClient: FusedLocationProviderClient
    ): UserLocationLocalDataSource = UserLocationLocalDataSourceImpl(fusedLocationProviderClient)
}