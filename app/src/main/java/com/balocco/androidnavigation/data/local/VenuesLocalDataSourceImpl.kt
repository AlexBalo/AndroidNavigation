package com.balocco.androidnavigation.data.local

import com.balocco.androidnavigation.data.model.Venue
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class VenuesLocalDataSourceImpl : VenuesLocalDataSource {

    private val venuesStorage: HashMap<String, Venue> = HashMap()
    private val venuesStorageSubject = PublishSubject.create<HashMap<String, Venue>>()

    override fun venuesStorageObservable(): Observable<HashMap<String, Venue>> =
        venuesStorageSubject

    override fun loadVenues(): HashMap<String, Venue> = venuesStorage

    override fun storeVenues(venues: List<Venue>): Completable =
        Completable.fromCallable {
            venues.forEach { venue -> venuesStorage[venue.id] = venue }
            venuesStorageSubject.onNext(venuesStorage)
        }
}