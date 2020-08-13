package com.balocco.androidnavigation.feature.venues.di

import com.balocco.androidnavigation.feature.venues.ui.VenuesFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        VenuesViewModelModule::class]
)
interface VenuesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): VenuesComponent
    }

    fun inject(fragment: VenuesFragment)
}