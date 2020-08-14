package com.balocco.androidnavigation.feature.detail.domain

import com.balocco.androidnavigation.data.model.Venue
import com.balocco.androidnavigation.feature.detail.viewmodel.VenueDetail
import javax.inject.Inject

private const val LINE_BREAK = "\n"

class VenueDetailMapper @Inject constructor() {

    fun mapFromVenue(venueDetail: VenueDetail, venue: Venue): VenueDetail {
        val venueDetailNew = venueDetail.copy()

        venueDetailNew.name = venue.name
        val addressBuilder = StringBuilder()
        venue.location.formattedAddress.forEach { addressLine ->
            if (addressBuilder.isNotEmpty()) {
                addressBuilder.append(LINE_BREAK)
            }
            addressBuilder.append(addressLine)
        }
        venueDetailNew.address = addressBuilder.toString()

        val categoriesBuilder = StringBuilder()
        venue.categories.forEach { venueCategory ->
            if (categoriesBuilder.isNotEmpty()) {
                categoriesBuilder.append(", ")
            }
            categoriesBuilder.append(venueCategory.name)
        }
        venueDetailNew.categories = categoriesBuilder.toString()
        venueDetailNew.rating = venue.rating?.toString() ?: ""
        venueDetailNew.description = venue.description ?: ""
        venueDetailNew.website = venue.url ?: ""

        val photo = venue.bestPhoto
        if (photo != null) {
            venueDetailNew.photoUrl = "${photo.prefix}${photo.width}x${photo.height}${photo.suffix}"
        }

        return venueDetailNew
    }
}