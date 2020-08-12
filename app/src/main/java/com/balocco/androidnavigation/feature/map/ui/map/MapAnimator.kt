package com.balocco.androidnavigation.feature.map.ui.map

import com.balocco.androidnavigation.data.model.Location

interface MapAnimator {

    fun initWithMap(map: Map)

    fun centerTo(userLocation: Location?)
}