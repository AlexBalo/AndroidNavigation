package com.balocco.androidnavigation.feature.venues.domain

import com.balocco.androidnavigation.data.local.VenuesLocalDataSource
import com.balocco.androidnavigation.data.remote.RemoteDataSource
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

private const val FOOD_CATEGORY_ID = "4d4b7105d754a06374d81259"

class FetchRestaurantsUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: VenuesLocalDataSource
) {

    operator fun invoke(center: String, radius: Double): Completable =
        remoteDataSource.fetchVenues(
            center, radius,
            FOOD_CATEGORY_ID
        )
            .onErrorComplete()
            .flatMapCompletable { venuesWrapper ->
                localDataSource.storeVenues(venuesWrapper.response.venues)
            }

}