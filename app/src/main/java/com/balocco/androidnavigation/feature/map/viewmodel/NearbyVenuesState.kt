package com.balocco.androidnavigation.feature.map.viewmodel

import com.balocco.androidnavigation.data.model.Venue

class NearbyVenuesState(
    val state: State,
    val venues: List<Venue> = mutableListOf()
) {
    enum class State {
        ERROR,
        SUCCESS
    }
}