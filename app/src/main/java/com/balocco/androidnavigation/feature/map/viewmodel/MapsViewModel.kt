package com.balocco.androidnavigation.feature.map.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.balocco.androidcomponents.common.scheduler.SchedulerProvider
import com.balocco.androidnavigation.common.viewmodel.BaseViewModel
import com.balocco.androidnavigation.data.local.UserLocationLocalDataSource
import com.balocco.androidnavigation.data.model.Location
import com.balocco.androidnavigation.feature.map.domain.FetchVenuesUseCase
import com.balocco.androidnavigation.feature.map.domain.LocationPermissionGrantedUseCase
import com.balocco.androidnavigation.feature.map.viewmodel.UserLocationState.State
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class MapsViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val userLocationLocalDataSource: UserLocationLocalDataSource,
    private val fetchVenuesUseCase: FetchVenuesUseCase,
    private val locationPermissionGrantedUseCase: LocationPermissionGrantedUseCase
) : BaseViewModel() {

    private var userLocationState: MutableLiveData<UserLocationState> = MutableLiveData()

    fun userLocationState(): LiveData<UserLocationState> = userLocationState

    fun onMapReady() {
        handleUserLocation()
    }

    fun onLocationPermissionResult(locationPermissionGranted: Boolean) {
        if (locationPermissionGranted) {
            handleUserLocation()
        } else {
            notifyUserLocationState(State.LOCATION_UNAVAILABLE)
        }
    }

    fun onMapCenterChanged(newCenter: Location, mapRadius: Double) {
        Log.e("Idle", "Center: ${newCenter.latitude} : ${newCenter.longitude}")
        Log.e("Idle", "MapRadius: $mapRadius")
    }

    private fun fetchVenues() {
        fetchVenuesUseCase()
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
}