package com.balocco.androidnavigation.feature.map.di

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.balocco.androidnavigation.common.navigation.MainNavigator
import com.balocco.androidnavigation.common.navigation.Navigator
import com.balocco.androidnavigation.di.ActivityScope
import com.balocco.androidnavigation.feature.venues.di.VenuesComponent
import dagger.Module
import dagger.Provides

@Module(
    subcomponents = [
        VenuesComponent::class]
)
class MapsSubcomponentsModule {

    @Provides
    @ActivityScope
    fun provideFragmentActivity(activity: Activity): FragmentActivity = activity as FragmentActivity

    @Provides
    @ActivityScope
    fun provideNavigator(activity: FragmentActivity): Navigator = MainNavigator(activity)
}