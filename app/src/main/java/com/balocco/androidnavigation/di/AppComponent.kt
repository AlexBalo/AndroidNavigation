package com.balocco.androidcomponents.di

import android.content.Context
import com.balocco.androidnavigation.AndroidNavigationApplication
import com.balocco.androidnavigation.di.AppSubcomponentsModule
import com.balocco.androidnavigation.feature.map.di.MapsComponent
import dagger.BindsInstance
import dagger.Component

/** Application component refers to application level modules only. */
@ApplicationScope
@Component(
    modules = [AppSubcomponentsModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    // Activities
    fun mapsComponent(): MapsComponent.Factory

    fun inject(application: AndroidNavigationApplication)

}