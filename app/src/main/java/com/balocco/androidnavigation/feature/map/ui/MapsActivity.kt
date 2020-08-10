package com.balocco.androidnavigation.feature.map.ui

import android.os.Bundle
import android.widget.Toast
import com.balocco.androidcomponents.di.AppComponent
import com.balocco.androidnavigation.R
import com.balocco.androidnavigation.common.permission.RequestPermissionsHelper
import com.balocco.androidnavigation.common.ui.BaseActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

private const val PERMISSION_REQUEST_ACCESS_LOCATION = 999

class MapsActivity : BaseActivity(), OnMapReadyCallback {

    @Inject lateinit var permissionsHelper: RequestPermissionsHelper

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        permissionsHelper = RequestPermissionsHelper(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onInject(appComponent: AppComponent) {
        appComponent.mapsComponent().create(this).inject(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionsHelper.onPermissionResultIsGranted(
                requestCode, PERMISSION_REQUEST_ACCESS_LOCATION, grantResults
            )
        ) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        permissionsHelper.requestPermission(
            RequestPermissionsHelper.Permission.FINE_LOCATION,
            PERMISSION_REQUEST_ACCESS_LOCATION,
            findViewById(R.id.root)
        )
    }
}