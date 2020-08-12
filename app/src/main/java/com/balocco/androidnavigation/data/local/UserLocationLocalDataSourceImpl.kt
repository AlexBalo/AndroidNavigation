package com.balocco.androidnavigation.data.local

import android.annotation.SuppressLint
import com.balocco.androidnavigation.data.model.Location
import com.google.android.gms.location.FusedLocationProviderClient
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class UserLocationLocalDataSourceImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : UserLocationLocalDataSource {

    private val locationSubject = PublishSubject.create<Location?>()

    override fun locationObservable(): Observable<Location?> =
        locationSubject.doOnSubscribe { lastLocation() }

    @SuppressLint("MissingPermission") private fun lastLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(::setLocation)
    }

    private fun setLocation(location: android.location.Location?) {
        if (location == null) {
            locationSubject.onError(Throwable())
        } else {
            val result = Location(longitude = location.longitude, latitude = location.latitude)
            locationSubject.onNext(result)
        }
    }

}