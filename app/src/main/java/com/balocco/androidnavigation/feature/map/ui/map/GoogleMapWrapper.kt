package com.balocco.androidnavigation.feature.map.ui.map

import com.balocco.androidnavigation.data.model.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.VisibleRegion
import kotlin.math.pow
import kotlin.math.sqrt

class GoogleMapWrapper(
    private val googleMap: GoogleMap
) : Map {

    override fun center(): Location {
        val center = googleMap.cameraPosition.target
        return Location(latitude = center.latitude, longitude = center.longitude)
    }

    override fun visibleRadiusInMeters(): Double {
        val visibleRegion: VisibleRegion = googleMap.projection.visibleRegion

        val distanceWidth = FloatArray(1)
        val distanceHeight = FloatArray(1)

        val farRight: LatLng = visibleRegion.farRight
        val farLeft: LatLng = visibleRegion.farLeft
        val nearRight: LatLng = visibleRegion.nearRight
        val nearLeft: LatLng = visibleRegion.nearLeft

        android.location.Location.distanceBetween(
            (farLeft.latitude + nearLeft.latitude) / 2,
            farLeft.longitude,
            (farRight.latitude + nearRight.latitude) / 2,
            farRight.longitude, distanceWidth
        )

        android.location.Location.distanceBetween(
            farRight.latitude,
            (farRight.longitude + farLeft.longitude) / 2,
            nearRight.latitude,
            (nearRight.longitude + nearLeft.longitude) / 2,
            distanceHeight
        )

        return sqrt(
            (distanceWidth[0].toString().toDouble().pow(2.0))
                    + distanceHeight[0].toString().toDouble().pow(2.0)
        ) / 2
    }

    override fun centerTo(location: Location, zoom: Float) {
        val currentLatLng = LatLng(location.latitude, location.longitude)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoom))
    }

}