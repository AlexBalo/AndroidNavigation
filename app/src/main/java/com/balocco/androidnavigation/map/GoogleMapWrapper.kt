package com.balocco.androidnavigation.map

import android.annotation.SuppressLint
import com.balocco.androidnavigation.data.model.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.VisibleRegion
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import kotlin.math.pow
import kotlin.math.sqrt

class GoogleMapWrapper(
    private val googleMap: GoogleMap
) : Map {

    private val mapIdleObservable: Subject<Unit> = PublishSubject.create()
    private val mapMarkerClickedObservable: Subject<Marker> = PublishSubject.create()
    private val mapMarkerInfoBubbleClickedObservable: Subject<Marker> = PublishSubject.create()

    override fun mapIdleObservable(): Observable<Unit> = mapIdleObservable

    override fun mapMarkerClickedObservable(): Observable<Marker> = mapMarkerClickedObservable

    override fun mapMarkerInfoBubbleClickedObservable(): Observable<Marker> =
        mapMarkerInfoBubbleClickedObservable

    // We need to suppress the warning even though we will enable to user location
    // only after verifying location permission was granted
    @SuppressLint("MissingPermission")
    override fun setUserLocationEnabled(enabled: Boolean) {
        googleMap.isMyLocationEnabled = true
    }

    override fun setMapIdleListener() {
        googleMap.setOnCameraIdleListener { mapIdleObservable.onNext(Unit) }
    }

    override fun setMapMarkerClickedListener() {
        googleMap.setOnMarkerClickListener { marker ->
            mapMarkerClickedObservable.onNext(GoogleMapMarker(marker))
            true
        }
    }

    override fun setMapInfoBubbleClickListener() {
        googleMap.setOnInfoWindowClickListener { marker ->
            mapMarkerClickedObservable.onNext(GoogleMapMarker(marker))
        }
    }

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

    override fun clearVenues() {
        googleMap.clear()
    }

    override fun showMarkers(markers: List<Marker>) {
        markers.forEach { marker ->
            val googleMarker = googleMap.addMarker(markerOptionFromMarker(marker))
            googleMarker.tag = marker.tag()
        }
    }

    private fun markerOptionFromMarker(marker: Marker): MarkerOptions =
        MarkerOptions()
            .position(LatLng(marker.location().latitude, marker.location().longitude))
            .title(marker.title())

}