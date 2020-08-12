package com.balocco.androidnavigation

import com.balocco.androidnavigation.data.model.Venue
import com.balocco.androidnavigation.data.model.VenueLocation

object TestUtils {

    fun createVenue(
        id: String = "1"
    ): Venue =
        Venue(
            id = id,
            name = "Name",
            location = createVenueLocation(),
            categories = listOf(),
            venuePage = null
        )

    fun createVenueLocation() =
        VenueLocation(
            latitude = 90.0,
            longitude = 180.0,
            formattedAddress = listOf()
        )
}