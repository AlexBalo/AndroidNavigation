package com.balocco.androidnavigation.map

class MapInteractorImpl : MapInteractor {

    private lateinit var map: Map
    override fun initWithMap(map: Map) {
        this.map = map
    }

    override fun setMapIdleListener(listener: MapIdleListener) {
        map.setMapIdleListener(listener)
    }

    override fun setMapMarkerClickedListener(listener: MapMarkerClickedListener) {
        map.setMapMarkerClickedListener(listener)
    }

    override fun setMapInfoBubbleClickListener(listener: MapInfoBubbleClickListener) {
        map.setMapInfoBubbleClickListener(listener)
    }
}