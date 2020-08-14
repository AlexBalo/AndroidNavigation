package com.balocco.androidnavigation.data.local

import com.balocco.androidnavigation.data.model.Venue
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface VenuesLocalDataSource {
    fun venuesObservable(): Observable<HashMap<String, Venue>>

    fun loadVenue(venueId: String): Single<Venue>

    // This method is required since without a database we need to keep results in a map
    fun loadVenues(): HashMap<String, Venue>

    fun storeVenues(venues: List<Venue>): Completable
}