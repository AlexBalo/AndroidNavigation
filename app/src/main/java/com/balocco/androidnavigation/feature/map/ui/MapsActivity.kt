package com.balocco.androidnavigation.feature.map.ui

import android.os.Bundle
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
import com.balocco.androidnavigation.feature.map.viewmodel.MapsViewModel
import com.balocco.androidnavigation.feature.map.viewmodel.NearbyVenuesState
import com.balocco.androidnavigation.feature.map.viewmodel.UserLocationState
import com.balocco.androidnavigation.feature.map.viewmodel.UserLocationState.State
import com.balocco.androidnavigation.map.*
import com.balocco.androidnavigation.map.Map
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
    @Inject lateinit var mapInteractor: MapInteractor
    @Inject lateinit var userLocationLayer: UserLocationLayer
    @Inject lateinit var venuesLayer: VenuesLayer
    @Inject lateinit var navigator: Navigator

    private lateinit var viewModel: MapsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap -> handleMapReadyEvent(GoogleMapWrapper(googleMap)) }

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

    private fun handleMapReadyEvent(map: Map) {
        mapAnimator.initWithMap(map)
        mapInfoProvider.initWithMap(map)
        mapVenuesDisplayer.initWithMap(map)
        mapInteractor.initWithMap(map)
        prepareMapListeners()
        viewModel.onMapReady()
    }

    private fun prepareMapListeners() {
        mapInteractor.setMapIdleListener(object : MapIdleListener {
            override fun onMapIdle() {
                viewModel.onMapCenterChanged(
                    mapInfoProvider.mapCenter(),
                    mapInfoProvider.mapVisibleRadiusInMeters()
                )
            }
        })
        mapInteractor.setMapMarkerClickedListener(object : MapMarkerClickedListener {
            override fun onMapMarkerClicked(marker: Marker) {
                venuesLayer.markerClicked(marker)
            }
        })
        mapInteractor.setMapInfoBubbleClickListener(object : MapInfoBubbleClickListener {
            override fun onInfoBubbleClicked(marker: Marker) {
                val detailFragment = DetailFragment()
                navigator.navigate(detailFragment)
            }
        })
    }

    private fun handleUserLocationState(userLocationState: UserLocationState) {
        when (userLocationState.state) {
            State.PERMISSION_REQUIRED -> requestLocationPermission()
            State.LOCATION_AVAILABLE -> {
                userLocationLayer.locationAvailable(userLocationState.userLocation)
            }
            State.LOCATION_UNAVAILABLE -> {
                userLocationLayer.locationUnavailable()
            }
        }
    }

    private fun handleNearbyVenuesState(nearbyVenuesState: NearbyVenuesState) {
        when (nearbyVenuesState.state) {
            NearbyVenuesState.State.SUCCESS -> {
                venuesLayer.newVenuesAvailable(nearbyVenuesState.venues)
            }
            NearbyVenuesState.State.ERROR -> {
                venuesLayer.errorLoadingVenues()
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