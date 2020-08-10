package com.balocco.androidnavigation.feature.map.viewmodel

import com.balocco.androidnavigation.data.model.Location

class UserLocationState(
    val state: State,
    val userLocation: Location? = null
) {
    enum class State {
        PERMISSION_REQUIRED,
        LOCATION_AVAILABLE,
        LOCATION_UNAVAILABLE
    }
}