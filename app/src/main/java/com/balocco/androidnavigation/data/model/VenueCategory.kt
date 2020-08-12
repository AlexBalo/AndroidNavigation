package com.balocco.androidnavigation.data.model

import com.google.gson.annotations.SerializedName

data class VenueCategory(
    @SerializedName("name") val name: String,
    @SerializedName("icon") val icon: Icon,
    @SerializedName("primary") val primary: Boolean
)