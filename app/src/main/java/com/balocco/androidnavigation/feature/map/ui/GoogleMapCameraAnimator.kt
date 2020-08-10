package com.balocco.androidnavigation.feature.map.ui

import com.balocco.androidnavigation.data.model.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

private const val USER_ZOOM = 15f

class GoogleMapCameraAnimator @Inject constructor() : MapCameraAnimator<GoogleMap> {

    private lateinit var map: GoogleMap

    override fun initWithMap(map: GoogleMap) {
        this.map = map
    }

    override fun moveToUserLocation(userLocation: Location?) {
        userLocation?.let { location ->
            val currentLatLng = LatLng(location.latitude, location.longitude)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, USER_ZOOM))
        }
    }

}