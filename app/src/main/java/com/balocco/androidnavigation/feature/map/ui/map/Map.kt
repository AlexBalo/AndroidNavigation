package com.balocco.androidnavigation.feature.map.ui.map

import com.balocco.androidnavigation.data.model.Location

interface Map {

    fun center(): Location

    fun visibleRadiusInMeters(): Double

    fun centerTo(location: Location, zoom: Float)
}