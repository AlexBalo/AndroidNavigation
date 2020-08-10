package com.balocco.androidnavigation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.balocco.androidcomponents.di.ApplicationScope
import com.balocco.androidnavigation.common.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider

/* Module that contains dependencies for view models. */
@Module
class ViewModelFactoryModule {

    @Provides
    @ApplicationScope
    fun provideViewModelFactory(providerMap: Map<Class<out ViewModel>, Provider<ViewModel>>):
            ViewModelProvider.Factory = ViewModelFactory(providerMap)
}