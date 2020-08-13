package com.balocco.androidnavigation.feature.venues.domain

import com.balocco.androidnavigation.TestUtils
import com.balocco.androidnavigation.data.local.VenuesLocalDataSource
import com.balocco.androidnavigation.data.remote.RemoteDataSource
import com.balocco.androidnavigation.data.remote.response.VenuesResponse
import com.balocco.androidnavigation.data.remote.response.VenuesResponseWrapper
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val CENTER = "15.9,14.8"
private const val RADIUS = 150.0
private const val FOOD_CATEGORY_ID = "4d4b7105d754a06374d81259"

class FetchRestaurantsUseCaseTest {

    @Mock lateinit var remoteDataSource: RemoteDataSource
    @Mock lateinit var localDataSource: VenuesLocalDataSource

    private lateinit var useCase: FetchRestaurantsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        useCase =
            FetchRestaurantsUseCase(
                remoteDataSource,
                localDataSource
            )
    }

    @Test
    fun `When fetching restaurants, stores results in local data source`() {
        val venues = listOf(TestUtils.createVenue())
        val venuesResponse = VenuesResponse(venues)
        val venuesResponseWrapper = VenuesResponseWrapper(venuesResponse)
        whenever(
            remoteDataSource.fetchVenues(
                CENTER,
                RADIUS,
                FOOD_CATEGORY_ID
            )
        )
            .thenReturn(Single.just(venuesResponseWrapper))
        whenever(localDataSource.storeVenues(venues)).thenReturn(Completable.complete())

        useCase(
            CENTER,
            RADIUS
        ).test().assertComplete()

        verify(localDataSource).storeVenues(venues)
    }

    @Test
    fun `When fetching restaurants with error, notify complete and does not store venues`() {
        whenever(
            remoteDataSource.fetchVenues(
                CENTER,
                RADIUS,
                FOOD_CATEGORY_ID
            )
        )
            .thenReturn(Single.error(Throwable()))

        useCase(
            CENTER,
            RADIUS
        ).test().assertComplete()

        verify(localDataSource, never()).storeVenues(any())
    }
}