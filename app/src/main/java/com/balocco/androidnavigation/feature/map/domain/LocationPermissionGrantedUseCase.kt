package com.balocco.androidnavigation.feature.map.domain

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.balocco.androidnavigation.common.permission.Permission
import javax.inject.Inject

class LocationPermissionGrantedUseCase @Inject constructor(
    private val context: Context
) {

    operator fun invoke(): Boolean =
        ContextCompat.checkSelfPermission(context, Permission.FINE_LOCATION.value) ==
                PackageManager.PERMISSION_GRANTED

}