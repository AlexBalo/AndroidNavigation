package com.balocco.androidnavigation.data.model

import com.google.gson.annotations.SerializedName

data class VenuePhoto(
    @SerializedName("prefix") val prefix: String,
    @SerializedName("suffix") val suffix: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
)