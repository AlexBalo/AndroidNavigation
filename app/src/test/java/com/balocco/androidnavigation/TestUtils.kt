package com.balocco.androidnavigation

import com.balocco.androidnavigation.data.model.*

object TestUtils {

    fun createVenue(
        id: String = "1",
        name: String = "Name",
        formattedAddress: List<String> = listOf(),
        categories: List<VenueCategory> = listOf(),
        rating: Double = 9.0,
        description: String = "This is the description",
        url: String = "",
        venuePhoto: VenuePhoto? = null
    ): Venue =
        Venue(
            id = id,
            name = name,
            location = createVenueLocation(formattedAddress),
            categories = categories,
            venuePage = null,
            url = url,
            description = description,
            rating = rating,
            bestPhoto = venuePhoto
        )

    fun createVenueCategory(
        name: String,
        icon: Icon = Icon("", ""),
        primary: Boolean = false
    ): VenueCategory =
        VenueCategory(
            name = name,
            icon = icon,
            primary = primary
        )

    private fun createVenueLocation(formattedAddress: List<String>) =
        VenueLocation(
            latitude = 90.0,
            longitude = 180.0,
            formattedAddress = formattedAddress
        )
}