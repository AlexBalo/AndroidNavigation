package com.balocco.androidnavigation.common.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.view.View
import androidx.core.app.ActivityCompat
import com.balocco.androidnavigation.R
import com.google.android.material.snackbar.Snackbar

class RequestPermissionsHelper constructor(
    private val activity: Activity
) {

    fun requestPermission(
        permission: Permission,
        requestCode: Int,
        containerView: View
    ) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission.value)) {
            Snackbar.make(
                containerView, permission.resourceForPermissionRationale(),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(R.string.dialog_confirm) {
                requestPermissionFromActivityOrFragment(permission, requestCode)
            }.show()
        } else {
            requestPermissionFromActivityOrFragment(permission, requestCode)
        }
    }

    fun onPermissionResultIsGranted(
        requestCode: Int,
        requestCodeToCheck: Int,
        grantResults: IntArray
    ): Boolean =
        requestCode == requestCodeToCheck && grantResults.size == 1 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED

    private fun requestPermissionFromActivityOrFragment(
        permission: Permission,
        requestCode: Int
    ) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission.value), requestCode)
    }

    private fun Permission.resourceForPermissionRationale(): Int {
        return when (this) {
            Permission.FINE_LOCATION -> R.string.access_location
        }
    }

    enum class Permission(val value: String) {
        FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}