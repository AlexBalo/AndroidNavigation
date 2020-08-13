package com.balocco.androidnavigation.map

import com.balocco.androidnavigation.data.model.Location

interface Map {

    fun setUserLocationEnabled(enabled: Boolean)

    fun setMapIdleListener(listener: MapIdleListener)

    fun setMapMarkerClickedListener(listener: MapMarkerClickedListener)

    fun setMapInfoBubbleClickListener(listener: MapInfoBubbleClickListener)

    fun center(): Location

    fun visibleRadiusInMeters(): Double

    fun centerTo(location: Location, zoom: Float)

    fun clearVenues()

    fun showMarkers(markers: List<Marker>)
}