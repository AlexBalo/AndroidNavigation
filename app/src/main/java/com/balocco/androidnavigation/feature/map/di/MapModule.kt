package com.balocco.androidnavigation.feature.map.di

import com.balocco.androidnavigation.di.ActivityScope
import com.balocco.androidnavigation.map.*
import dagger.Module
import dagger.Provides

@Module
class MapModule {

    @Provides
    @ActivityScope
    fun provideMapInfoProvider(): MapInfoProvider = MapInfoProviderImpl()

    @Provides
    @ActivityScope
    fun provideMapAnimator(): MapAnimator = MapAnimatorImpl()

    @Provides
    @ActivityScope
    fun provideMapVenuesDisplayer(): MapVenuesDisplayer = MapVenuesDisplayerImpl()

    @Provides
    @ActivityScope
    fun provideMapInteractor(): MapInteractor = MapInteractorImpl()
}