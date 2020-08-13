package com.balocco.androidnavigation.map

import com.balocco.androidnavigation.data.model.Venue
import javax.inject.Inject

class VenuesLayer @Inject constructor(
    private val mapVenuesDisplayer: MapVenuesDisplayer
) {

    fun newVenuesAvailable(venues: List<Venue>) {
        mapVenuesDisplayer.clearVenues()
        val markers = mutableListOf<Marker>()
        venues.forEach { venue -> markers.add(VenueMarker(venue)) }
        mapVenuesDisplayer.showMarkers(markers)
    }

    fun errorLoadingVenues() {
        // At the moment we don't do anything in this circumstance
    }

    fun markerClicked(marker: Marker) {
        marker.showInfoBubble()
    }

}