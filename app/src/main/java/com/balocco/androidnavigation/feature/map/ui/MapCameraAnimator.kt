package com.balocco.androidnavigation.feature.map.ui

import com.balocco.androidnavigation.data.model.Location

interface MapCameraAnimator<T> {

    fun initWithMap(map: T)

    fun moveToUserLocation(userLocation: Location?)
}