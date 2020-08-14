package com.balocco.androidnavigation.feature.venues.di

import androidx.lifecycle.ViewModel
import com.balocco.androidnavigation.di.ViewModelKey
import com.balocco.androidnavigation.feature.venues.viewmodel.VenuesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class VenuesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(VenuesViewModel::class)
    abstract fun bindVenuesViewModel(venuesViewModel: VenuesViewModel): ViewModel
}