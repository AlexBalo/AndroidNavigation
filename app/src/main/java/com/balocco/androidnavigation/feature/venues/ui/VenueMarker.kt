package com.balocco.androidnavigation.feature.venues.ui

import com.balocco.androidnavigation.data.model.Location
import com.balocco.androidnavigation.data.model.Venue
import com.balocco.androidnavigation.map.Marker

class VenueMarker(
    private val venue: Venue
) : Marker {
    override fun showInfoBubble() {}

    override fun location(): Location =
        Location(
            latitude = venue.location.latitude,
            longitude = venue.location.longitude
        )

    override fun title(): String = venue.name

    override fun tag(): Any? = venue

}