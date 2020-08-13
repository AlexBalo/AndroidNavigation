package com.balocco.androidnavigation.feature.venues.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.balocco.androidcomponents.common.scheduler.SchedulerProvider
import com.balocco.androidnavigation.common.viewmodel.BaseViewModel
import com.balocco.androidnavigation.data.model.Location
import com.balocco.androidnavigation.data.model.Venue
import com.balocco.androidnavigation.feature.venues.domain.FetchRestaurantsUseCase
import com.balocco.androidnavigation.feature.venues.domain.NearbyVenuesProvider
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class VenuesViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val nearbyVenuesProvider: NearbyVenuesProvider,
    private val fetchRestaurantsUseCase: FetchRestaurantsUseCase
) : BaseViewModel() {

    private var nearbyVenuesState: MutableLiveData<NearbyVenuesState> = MutableLiveData()

    fun nearbyVenuesState(): LiveData<NearbyVenuesState> = nearbyVenuesState

    fun start() {
        nearbyVenuesProvider.venuesObservable()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { results -> notifyNearbyVenuesState(NearbyVenuesState.State.SUCCESS, results) },
                { notifyNearbyVenuesState(NearbyVenuesState.State.ERROR) }
            )
            .addTo(compositeDisposable)
    }

    fun onMapCenterChanged(newCenter: Location, mapRadius: Double) {
        nearbyVenuesProvider.updateProximityInfo(newCenter, mapRadius)
        fetchRestaurants(newCenter.literal(), mapRadius)
    }

    private fun fetchRestaurants(center: String, radius: Double) {
        fetchRestaurantsUseCase(center, radius)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe()
            .addTo(compositeDisposable)
    }

    private fun notifyNearbyVenuesState(
        state: NearbyVenuesState.State,
        venues: List<Venue> = mutableListOf()
    ) {
        nearbyVenuesState.value =
            NearbyVenuesState(
                state,
                venues
            )
    }
}