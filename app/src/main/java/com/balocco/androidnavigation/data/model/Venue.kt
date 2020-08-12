package com.balocco.androidnavigation.data.model

import com.google.gson.annotations.SerializedName

data class Venue(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: VenueLocation,
    @SerializedName("categories") val categories: List<VenueCategory>,
    @SerializedName("venuePage") val venuePage: VenuePage?
)