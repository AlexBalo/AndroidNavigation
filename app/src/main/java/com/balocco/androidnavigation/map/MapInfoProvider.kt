package com.balocco.androidnavigation.map

import com.balocco.androidnavigation.data.model.Location

interface MapInfoProvider {

    fun initWithMap(map: Map)

    fun setUserLocationEnabled(enabled: Boolean)

    fun mapCenter(): Location

    fun mapVisibleRadiusInMeters(): Double
}