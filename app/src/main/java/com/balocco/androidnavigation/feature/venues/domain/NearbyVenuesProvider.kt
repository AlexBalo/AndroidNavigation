package com.balocco.androidnavigation.feature.venues.domain

import com.balocco.androidnavigation.data.local.VenuesLocalDataSource
import com.balocco.androidnavigation.data.model.Location
import com.balocco.androidnavigation.data.model.Venue
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

class NearbyVenuesProvider @Inject constructor(
    private val venuesLocalDataSource: VenuesLocalDataSource,
    private val venueWithinRadiusUseCase: VenueWithinRadiusUseCase
) {

    private val venuesSubject = PublishSubject.create<HashMap<String, Venue>>()

    private var center: Location = Location(latitude = 0.0, longitude = 0.0)
    private var visibleRadius: Double = 0.0

    fun venuesObservable(): @NonNull Observable<List<Venue>> {
        val venuesObservable = venuesSubject.map { map -> map.values }
        val venuesFromStorage =
            venuesLocalDataSource.venuesStorageObservable().map { map -> map.values }
        return Observable.merge(venuesObservable, venuesFromStorage)
            .map { venue -> venue.filter { venueToFilter -> isVenueWithinRadius(venueToFilter) } }
    }

    fun updateProximityInfo(newCenter: Location, newRadius: Double) {
        center = newCenter
        visibleRadius = newRadius
        venuesSubject.onNext(venuesLocalDataSource.loadVenues())
    }

    private fun isVenueWithinRadius(venue: Venue): Boolean =
        venueWithinRadiusUseCase.invoke(venue, center, visibleRadius)
}