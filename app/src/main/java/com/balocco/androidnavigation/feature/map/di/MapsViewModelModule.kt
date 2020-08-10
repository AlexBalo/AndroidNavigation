package com.balocco.androidnavigation.feature.map.di

import androidx.lifecycle.ViewModel
import com.balocco.androidnavigation.di.ViewModelKey
import com.balocco.androidnavigation.feature.map.viewmodel.MapsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MapsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MapsViewModel::class)
    abstract fun bindMapsViewModel(mapsViewModel: MapsViewModel): ViewModel
}