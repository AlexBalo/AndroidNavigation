package com.balocco.androidnavigation.data.remote

import com.balocco.androidnavigation.data.remote.response.VenuesResponseWrapper
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL_INTERNAL = "https://api.foursquare.com/"
private const val API_VERSION = "v2"

interface RemoteDataSource {
    @GET("${API_VERSION}/venues/search")
    fun fetchVenues(
        @Query("ll") center: String,
        @Query("radius") radius: Double,
        @Query("limit") limit: Int = 20
    ): Single<VenuesResponseWrapper>

    companion object {
        const val BASE_URL = BASE_URL_INTERNAL
        const val LAST_API_VERSION = "20200812"
    }
}