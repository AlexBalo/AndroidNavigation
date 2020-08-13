package com.balocco.androidnavigation.feature.map.ui.map

import com.balocco.androidnavigation.data.model.Venue
import javax.inject.Inject

class MapVenuesDisplayerImpl @Inject constructor() : MapVenuesDisplayer {

    private lateinit var map: Map

    override fun initWithMap(map: Map) {
        this.map = map
    }

    override fun clearVenues() {
        map.clearVenues()
    }

    override fun showVenues(venues: List<Venue>) {
        map.showVenues(venues)
    }

}