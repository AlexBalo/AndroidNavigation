package com.balocco.androidnavigation.map

import com.balocco.androidnavigation.data.model.Location
import javax.inject.Inject

class UserLocationLayer @Inject constructor(
    private val mapInfoProvider: MapInfoProvider,
    private val mapAnimator: MapAnimator
) {

    fun locationAvailable(userLocation: Location?) {
        mapInfoProvider.setUserLocationEnabled(true)
        mapAnimator.centerTo(userLocation)
    }

    fun locationUnavailable() {
        // At the moment we don't do anything in this circumstance
    }

}