package com.balocco.androidnavigation.feature.map.ui.map

import com.balocco.androidnavigation.data.model.Location
import javax.inject.Inject

private const val DEFAULT_ZOOM = 15f

class MapAnimatorImpl @Inject constructor() : MapAnimator {

    private lateinit var map: Map

    override fun initWithMap(map: Map) {
        this.map = map
    }

    override fun centerTo(newLocation: Location?) {
        newLocation?.let { location ->
            map.centerTo(location, DEFAULT_ZOOM)
        }
    }

}