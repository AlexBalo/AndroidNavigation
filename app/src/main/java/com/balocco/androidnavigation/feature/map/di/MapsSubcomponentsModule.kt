package com.balocco.androidnavigation.feature.map.di

import com.balocco.androidnavigation.di.ActivityScope
import com.balocco.androidnavigation.feature.map.ui.map.MapAnimator
import com.balocco.androidnavigation.feature.map.ui.map.MapAnimatorImpl
import com.balocco.androidnavigation.feature.map.ui.map.MapInfoProvider
import com.balocco.androidnavigation.feature.map.ui.map.MapInfoProviderImpl
import dagger.Module
import dagger.Provides

@Module
class MapsSubcomponentsModule {

    @Provides
    @ActivityScope
    fun provideMapInfoProvider(): MapInfoProvider = MapInfoProviderImpl()

    @Provides
    @ActivityScope
    fun provideMapAnimator(): MapAnimator = MapAnimatorImpl()
}