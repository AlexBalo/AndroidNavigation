package com.balocco.androidnavigation.map

import io.reactivex.rxjava3.core.Observable

interface MapInteractor {

    fun initWithMap(map: Map)

    fun mapIdleObservable(): Observable<Unit>

    fun mapMarkerClickedObservable(): Observable<Marker>

    fun mapMarkerInfoBubbleClickedObservable(): Observable<Marker>

    fun setMapIdleListener()

    fun setMapMarkerClickedListener()

    fun setMapInfoBubbleClickListener()


}