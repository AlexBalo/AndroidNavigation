package com.balocco.androidnavigation.feature.map.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.balocco.androidcomponents.di.AppComponent
import com.balocco.androidnavigation.R
import com.balocco.androidnavigation.common.permission.Permission
import com.balocco.androidnavigation.common.permission.RequestPermissionsHelper
import com.balocco.androidnavigation.common.ui.BaseActivity
import com.balocco.androidnavigation.common.viewmodel.ViewModelFactory
import com.balocco.androidnavigation.feature.map.viewmodel.MapsViewModel
import com.balocco.androidnavigation.feature.map.viewmodel.UserLocationState
import com.balocco.androidnavigation.feature.map.viewmodel.UserLocationState.State
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import javax.inject.Inject

private const val PERMISSION_REQUEST_ACCESS_LOCATION = 999

class MapsActivity : BaseActivity(), OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var permissionsHelper: RequestPermissionsHelper
    @Inject lateinit var mapCameraAnimator: GoogleMapCameraAnimator

    private lateinit var map: GoogleMap
    private lateinit var viewModel: MapsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(MapsViewModel::class.java)
        viewModel.userLocationState()
            .observe(this, Observer { state -> handleUserLocationState(state) })
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

    override fun onMapReady(googleMap: GoogleMap) {
        mapCameraAnimator.initWithMap(googleMap)
        map = googleMap
        viewModel.onMapReady()
    }

    // We need to suppress the warning even though we will enable to user location
    // only after verifying location permission was granted
    @SuppressLint("MissingPermission")
    private fun handleUserLocationState(userLocationState: UserLocationState) {
        when (userLocationState.state) {
            State.PERMISSION_REQUIRED -> requestLocationPermission()
            State.LOCATION_AVAILABLE -> {
                map.isMyLocationEnabled = true
                mapCameraAnimator.moveToUserLocation(userLocationState.userLocation)
            }
            State.LOCATION_UNAVAILABLE -> { /* At the moment we don't do anything */
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