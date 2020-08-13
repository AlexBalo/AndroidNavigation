package com.balocco.androidnavigation.map

import com.balocco.androidnavigation.data.model.Location

interface MapAnimator {

    fun initWithMap(map: Map)

    fun centerTo(newLocation: Location?)
}