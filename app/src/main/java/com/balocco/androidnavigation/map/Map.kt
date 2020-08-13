package com.balocco.androidnavigation.map

import com.balocco.androidnavigation.data.model.Location
import io.reactivex.rxjava3.core.Observable

interface Map {

    fun mapIdleObservable(): Observable<Unit>

    fun mapMarkerClickedObservable(): Observable<Marker>

    fun mapMarkerInfoBubbleClickedObservable(): Observable<Marker>

    fun setUserLocationEnabled(enabled: Boolean)

    fun setMapIdleListener()

    fun setMapMarkerClickedListener()

    fun setMapInfoBubbleClickListener()

    fun center(): Location

    fun visibleRadiusInMeters(): Double

    fun centerTo(location: Location, zoom: Float)

    fun clearVenues()

    fun showMarkers(markers: List<Marker>)
}