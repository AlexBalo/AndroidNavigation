package com.balocco.androidnavigation.feature.map.ui.map

import com.balocco.androidnavigation.data.model.Location

interface MapInfoProvider {

    fun initWithMap(map: Map)

    fun mapCenter(): Location

    fun mapVisibleRadiusInMeters(): Double
}