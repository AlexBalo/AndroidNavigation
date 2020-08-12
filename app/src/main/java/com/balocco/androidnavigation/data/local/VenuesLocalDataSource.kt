package com.balocco.androidnavigation.data.local

import com.balocco.androidnavigation.data.model.Venue
import io.reactivex.rxjava3.core.Completable

interface VenuesLocalDataSource {
    fun storeVenues(venues: List<Venue>): Completable
}