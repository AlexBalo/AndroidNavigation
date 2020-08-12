package com.balocco.androidnavigation.di

import com.balocco.androidcomponents.di.ApplicationScope
import com.balocco.androidnavigation.data.local.VenuesLocalDataSource
import com.balocco.androidnavigation.data.local.VenuesLocalDataSourceImpl
import dagger.Module
import dagger.Provides

/* Module that contains dependencies for data accessing. */
@Module
class DataModule {

    @Provides
    @ApplicationScope
    fun provideVenuesLocalDataSource(): VenuesLocalDataSource = VenuesLocalDataSourceImpl()
}