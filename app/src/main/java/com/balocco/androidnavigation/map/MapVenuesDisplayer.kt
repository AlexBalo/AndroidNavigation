package com.balocco.androidnavigation.map

interface MapVenuesDisplayer {

    fun initWithMap(map: Map)

    fun clearVenues()

    fun showMarkers(markers: List<Marker>)
}