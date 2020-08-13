package com.balocco.androidnavigation.map

import com.balocco.androidnavigation.data.model.Location
import com.balocco.androidnavigation.data.model.Venue

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