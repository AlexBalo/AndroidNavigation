package com.balocco.androidnavigation.data.model

import com.google.gson.annotations.SerializedName

data class VenueLocation(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lng") val longitude: Double,
    @SerializedName("formattedAddress") val formattedAddress: List<String>
)