package com.balocco.androidnavigation.feature.detail.viewmodel

class DetailState(
    val state: State,
    val venue: VenueDetail? = null
) {
    enum class State {
        ERROR,
        SUCCESS
    }
}