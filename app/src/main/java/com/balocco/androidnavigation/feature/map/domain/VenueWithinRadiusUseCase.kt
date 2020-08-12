package com.balocco.androidnavigation.feature.map.domain

import com.balocco.androidnavigation.data.model.Location
import com.balocco.androidnavigation.data.model.Venue
import javax.inject.Inject

class VenueWithinRadiusUseCase @Inject constructor() {

    operator fun invoke(
        venue: Venue,
        firstLocation: Location,
        radius: Double
    ): Boolean {
        val results = FloatArray(1)
        android.location.Location.distanceBetween(
            firstLocation.latitude,
            firstLocation.longitude,
            venue.location.latitude,
            venue.location.longitude,
            results
        )
        return results[0] <= radius
    }
}