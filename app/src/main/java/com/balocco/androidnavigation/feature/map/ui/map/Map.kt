package com.balocco.androidnavigation.feature.map.ui.map

import com.balocco.androidnavigation.data.model.Location
import com.balocco.androidnavigation.data.model.Venue

interface Map {

    fun center(): Location

    fun visibleRadiusInMeters(): Double

    fun centerTo(location: Location, zoom: Float)

    fun clearVenues()

    fun showVenues(venues: List<Venue>)
}