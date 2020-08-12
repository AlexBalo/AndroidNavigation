package com.balocco.androidnavigation.feature.map.ui.map

import com.balocco.androidnavigation.data.model.Location
import javax.inject.Inject

class MapInfoProviderImpl @Inject constructor() : MapInfoProvider {

    private lateinit var map: Map

    override fun initWithMap(map: Map) {
        this.map = map
    }

    override fun mapCenter(): Location = map.center()

    override fun mapVisibleRadiusInMeters(): Double = map.visibleRadiusInMeters()

}