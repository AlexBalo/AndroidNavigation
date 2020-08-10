package com.balocco.androidnavigation.di

import com.balocco.androidnavigation.feature.map.di.MapsComponent
import dagger.Module

@Module(
    subcomponents = [
        MapsComponent::class]
)
class AppSubcomponentsModule