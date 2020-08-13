package com.balocco.androidnavigation.feature.map.ui.map

import com.balocco.androidnavigation.data.model.Venue

interface MapVenuesDisplayer {

    fun initWithMap(map: Map)

    fun clearVenues()

    fun showVenues(venues: List<Venue>)
}