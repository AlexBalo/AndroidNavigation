package com.balocco.androidnavigation.data.remote.response

import com.google.gson.annotations.SerializedName

data class VenueResponseWrapper(
    @SerializedName("response") val response: VenueResponse
)