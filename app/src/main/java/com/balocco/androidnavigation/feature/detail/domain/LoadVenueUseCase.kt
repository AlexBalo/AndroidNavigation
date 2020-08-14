package com.balocco.androidnavigation.feature.detail.domain

import com.balocco.androidnavigation.data.local.VenuesLocalDataSource
import com.balocco.androidnavigation.data.model.Venue
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoadVenueUseCase @Inject constructor(
    private val localDataSource: VenuesLocalDataSource
) {

    operator fun invoke(venueId: String): Single<Venue> = localDataSource.loadVenue(venueId)

}