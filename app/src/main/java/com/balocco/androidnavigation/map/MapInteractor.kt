package com.balocco.androidnavigation.map

interface MapInteractor {

    fun initWithMap(map: Map)

    fun setMapIdleListener(listener: MapIdleListener)

    fun setMapMarkerClickedListener(listener: MapMarkerClickedListener)

    fun setMapInfoBubbleClickListener(listener: MapInfoBubbleClickListener)


}