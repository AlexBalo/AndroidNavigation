package com.balocco.androidnavigation.feature.map.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.balocco.androidcomponents.common.scheduler.SchedulerProvider
import com.balocco.androidnavigation.common.viewmodel.BaseViewModel
import com.balocco.androidnavigation.data.local.UserLocationLocalDataSource
import com.balocco.androidnavigation.data.model.Location
import com.balocco.androidnavigation.data.model.Venue
import com.balocco.androidnavigation.feature.map.domain.FetchRestaurantsUseCase
import com.balocco.androidnavigation.feature.map.domain.LocationPermissionGrantedUseCase
import com.balocco.androidnavigation.feature.map.domain.NearbyVenuesProvider
import com.balocco.androidnavigation.feature.map.viewmodel.UserLocationState.State
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class MapsViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val userLocationLocalDataSource: UserLocationLocalDataSource,
    private val nearbyVenuesProvider: NearbyVenuesProvider,
    private val fetchRestaurantsUseCase: FetchRestaurantsUseCase,
    private val locationPermissionGrantedUseCase: LocationPermissionGrantedUseCase
) : BaseViewModel() {

    private var userLocationState: MutableLiveData<UserLocationState> = MutableLiveData()
    private var nearbyVenuesState: MutableLiveData<NearbyVenuesState> = MutableLiveData()

    fun userLocationState(): LiveData<UserLocationState> = userLocationState
    fun nearbyVenuesState(): LiveData<NearbyVenuesState> = nearbyVenuesState

    fun onMapReady() {
        handleUserLocation()
        nearbyVenuesProvider.venuesObservable()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { results -> notifyNearbyVenuesState(NearbyVenuesState.State.SUCCESS, results) },
                { notifyNearbyVenuesState(NearbyVenuesState.State.ERROR) }
            )
            .addTo(compositeDisposable)
    }

    fun onLocationPermissionResult(locationPermissionGranted: Boolean) {
        if (locationPermissionGranted) {
            handleUserLocation()
        } else {
            notifyUserLocationState(State.LOCATION_UNAVAILABLE)
        }
    }

    fun onMapCenterChanged(newCenter: Location, mapRadius: Double) {
        nearbyVenuesProvider.updateProximityInfo(newCenter, mapRadius)
        fetchVenues(newCenter.literal(), mapRadius)
    }

    private fun fetchVenues(center: String, radius: Double) {
        fetchRestaurantsUseCase(center, radius)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe()
            .addTo(compositeDisposable)
    }

    private fun handleUserLocation() {
        if (locationPermissionGrantedUseCase()) {
            userLocationLocalDataSource.locationObservable()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    { location -> notifyUserLocationState(State.LOCATION_AVAILABLE, location) },
                    { notifyUserLocationState(State.LOCATION_AVAILABLE) }
                ).addTo(compositeDisposable)
        } else {
            notifyUserLocationState(State.PERMISSION_REQUIRED)
        }
    }

    private fun notifyUserLocationState(state: State, location: Location? = null) {
        userLocationState.value = UserLocationState(state, location)
    }

    private fun notifyNearbyVenuesState(
        state: NearbyVenuesState.State,
        venues: List<Venue> = mutableListOf()
    ) {
        nearbyVenuesState.value = NearbyVenuesState(state, venues)
    }
}