package com.balocco.androidnavigation.data.remote.response

import com.balocco.androidnavigation.data.model.Venue
import com.google.gson.annotations.SerializedName

data class VenuesResponse(
    @SerializedName("venues") val venues: List<Venue>
)