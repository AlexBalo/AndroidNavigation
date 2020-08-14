package com.balocco.androidnavigation.feature.detail.di

import com.balocco.androidnavigation.feature.detail.ui.DetailFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        DetailViewModelModule::class]
)
interface DetailComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailComponent
    }

    fun inject(fragment: DetailFragment)
}