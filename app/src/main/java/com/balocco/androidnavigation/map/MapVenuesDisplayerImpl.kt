package com.balocco.androidnavigation.map

import javax.inject.Inject

class MapVenuesDisplayerImpl @Inject constructor() : MapVenuesDisplayer {

    private lateinit var map: Map

    override fun initWithMap(map: Map) {
        this.map = map
    }

    override fun clearVenues() {
        map.clearVenues()
    }

    override fun showMarkers(markers: List<Marker>) {
        map.showMarkers(markers)
    }

}