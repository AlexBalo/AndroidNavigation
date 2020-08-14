package com.balocco.androidnavigation

import com.balocco.androidnavigation.data.model.Icon
import com.balocco.androidnavigation.data.model.Venue
import com.balocco.androidnavigation.data.model.VenueCategory
import com.balocco.androidnavigation.data.model.VenueLocation

object TestUtils {

    fun createVenue(
        id: String = "1",
        name: String = "Name",
        formattedAddress: List<String> = listOf(),
        categories: List<VenueCategory> = listOf()
    ): Venue =
        Venue(
            id = id,
            name = name,
            location = createVenueLocation(formattedAddress),
            categories = categories,
            venuePage = null
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