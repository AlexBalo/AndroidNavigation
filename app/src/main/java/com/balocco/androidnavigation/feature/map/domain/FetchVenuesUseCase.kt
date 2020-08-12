package com.balocco.androidnavigation.feature.map.domain

import com.balocco.androidnavigation.data.local.VenuesLocalDataSource
import com.balocco.androidnavigation.data.remote.RemoteDataSource
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class FetchVenuesUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: VenuesLocalDataSource
) {

    operator fun invoke(center: String, radius: Double): Completable =
        remoteDataSource.fetchVenues(center, radius)
            .onErrorComplete()
            .flatMapCompletable { venuesWrapper ->
                localDataSource.storeVenues(venuesWrapper.response.venues)
            }

}