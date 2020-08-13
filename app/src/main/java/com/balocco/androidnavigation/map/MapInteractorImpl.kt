package com.balocco.androidnavigation.map

import io.reactivex.rxjava3.core.Observable

class MapInteractorImpl : MapInteractor {

    private lateinit var map: Map

    override fun initWithMap(map: Map) {
        this.map = map
    }

    override fun mapIdleObservable(): Observable<Unit> = map.mapIdleObservable()

    override fun mapMarkerClickedObservable(): Observable<Marker> = map.mapMarkerClickedObservable()

    override fun mapMarkerInfoBubbleClickedObservable(): Observable<Marker> =
        map.mapMarkerInfoBubbleClickedObservable()

    override fun setMapIdleListener() {
        map.setMapIdleListener()
    }

    override fun setMapMarkerClickedListener() {
        map.setMapMarkerClickedListener()
    }

    override fun setMapInfoBubbleClickListener() {
        map.setMapInfoBubbleClickListener()
    }
}