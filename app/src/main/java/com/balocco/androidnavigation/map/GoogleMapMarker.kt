package com.balocco.androidnavigation.map

import com.balocco.androidnavigation.data.model.Location

class GoogleMapMarker(
    private val marker: com.google.android.gms.maps.model.Marker
) : Marker {

    override fun showInfoBubble() {
        marker.showInfoWindow()
    }

    override fun location(): Location =
        Location(
            latitude = marker.position.latitude,
            longitude = marker.position.longitude
        )

    override fun title(): String = marker.title

    override fun tag(): Any? = marker.tag

}