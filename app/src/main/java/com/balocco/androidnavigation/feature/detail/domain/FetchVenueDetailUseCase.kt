package com.balocco.androidnavigation.feature.detail.domain

import com.balocco.androidnavigation.data.model.Venue
import com.balocco.androidnavigation.data.remote.RemoteDataSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FetchVenueDetailUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    operator fun invoke(venueId: String): Single<Venue> =
        remoteDataSource.fetchVenueDetail(venueId)
            .map { venueResponseWrapper -> venueResponseWrapper.response.venue }

}