package com.balocco.androidnavigation.feature.map.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.balocco.androidcomponents.di.AppComponent
import com.balocco.androidnavigation.R
import com.balocco.androidnavigation.common.navigation.Navigator
import com.balocco.androidnavigation.common.permission.Permission
import com.balocco.androidnavigation.common.permission.RequestPermissionsHelper
import com.balocco.androidnavigation.common.ui.BaseActivity
import com.balocco.androidnavigation.common.viewmodel.ViewModelFactory
import com.balocco.androidnavigation.feature.detail.ui.DetailFragment
import com.balocco.androidnavigation.feature.map.ui.map.GoogleMapWrapper
import com.balocco.androidnavigation.feature.map.ui.map.MapAnimator
import com.balocco.androidnavigation.feature.map.ui.map.MapInfoProvider
import com.balocco.androidnavigation.feature.map.ui.map.MapVenuesDisplayer
import com.balocco.androidnavigation.feature.map.viewmodel.MapsViewModel
import com.balocco.androidnavigation.feature.map.viewmodel.NearbyVenuesState
import com.balocco.androidnavigation.feature.map.viewmodel.UserLocationState
import com.balocco.androidnavigation.feature.map.viewmodel.UserLocationState.State
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import javax.inject.Inject

private const val PERMISSION_REQUEST_ACCESS_LOCATION = 999

class MapsActivity : BaseActivity(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var permissionsHelper: RequestPermissionsHelper
    @Inject lateinit var mapInfoProvider: MapInfoProvider
    @Inject lateinit var mapAnimator: MapAnimator
    @Inject lateinit var mapVenuesDisplayer: MapVenuesDisplayer
    @Inject lateinit var navigator: Navigator

    private lateinit var map: GoogleMap
    private lateinit var viewModel: MapsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap -> handleGoogleMapReadyEvent(googleMap) }

        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(MapsViewModel::class.java)
        viewModel.userLocationState()
            .observe(this, Observer { state -> handleUserLocationState(state) })
        viewModel.nearbyVenuesState()
            .observe(this, Observer { state -> handleNearbyVenuesState(state) })
    }

    override fun onInject(appComponent: AppComponent) {
        appComponent.mapsComponent().create(this).inject(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
            val locationPermissionGranted = permissionsHelper.onPermissionResultIsGranted(
                requestCode, PERMISSION_REQUEST_ACCESS_LOCATION, grantResults
            )
            viewModel.onLocationPermissionResult(locationPermissionGranted)
        }
    }

    private fun handleGoogleMapReadyEvent(googleMap: GoogleMap) {
        val mapInUse = GoogleMapWrapper(googleMap)
        mapAnimator.initWithMap(mapInUse)
        mapInfoProvider.initWithMap(mapInUse)
        mapVenuesDisplayer.initWithMap(mapInUse)
        map = googleMap
        prepareGoogleMapListeners()
        viewModel.onMapReady()
    }

    private fun prepareGoogleMapListeners() {
        map.setOnCameraIdleListener {
            viewModel.onMapCenterChanged(
                mapInfoProvider.mapCenter(),
                mapInfoProvider.mapVisibleRadiusInMeters()
            )
        }
        map.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }
        map.setOnInfoWindowClickListener { marker ->
            Log.e("MarkerInfoWindow", "MarkerInfoWindow clicked: ${marker.title}")
            val detailFragment = DetailFragment()
            navigator.navigate(detailFragment)
        }
    }

    // We need to suppress the warning even though we will enable to user location
    // only after verifying location permission was granted
    @SuppressLint("MissingPermission")
    private fun handleUserLocationState(userLocationState: UserLocationState) {
        when (userLocationState.state) {
            State.PERMISSION_REQUIRED -> requestLocationPermission()
            State.LOCATION_AVAILABLE -> {
                map.isMyLocationEnabled = true
                mapAnimator.centerTo(userLocationState.userLocation)
            }
            State.LOCATION_UNAVAILABLE -> { /* At the moment we don't do anything */
            }
        }
    }

    private fun handleNearbyVenuesState(nearbyVenuesState: NearbyVenuesState) {
        when (nearbyVenuesState.state) {
            NearbyVenuesState.State.SUCCESS -> {
                mapVenuesDisplayer.clearVenues()
                mapVenuesDisplayer.showVenues(nearbyVenuesState.venues)
            }
            NearbyVenuesState.State.ERROR -> { /* For now we do not react to errors */
            }
        }
    }

    private fun requestLocationPermission() {
        permissionsHelper.requestPermission(
            Permission.FINE_LOCATION,
            PERMISSION_REQUEST_ACCESS_LOCATION,
            findViewById(R.id.root)
        )
    }
}