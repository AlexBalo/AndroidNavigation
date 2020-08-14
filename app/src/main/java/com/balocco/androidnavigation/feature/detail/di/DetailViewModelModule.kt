package com.balocco.androidnavigation.feature.detail.di

import androidx.lifecycle.ViewModel
import com.balocco.androidnavigation.di.ViewModelKey
import com.balocco.androidnavigation.feature.detail.viewmodel.DetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel): ViewModel
}