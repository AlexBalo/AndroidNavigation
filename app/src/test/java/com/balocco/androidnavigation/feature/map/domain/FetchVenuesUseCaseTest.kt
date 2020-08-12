package com.balocco.androidnavigation.feature.map.domain

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

class FetchVenuesUseCaseTest {

    @Mock lateinit var remoteDataSource: RemoteDataSource
    @Mock lateinit var localDataSource: VenuesLocalDataSource

    private lateinit var useCase: FetchVenuesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        useCase = FetchVenuesUseCase(remoteDataSource, localDataSource)
    }

    @Test
    fun `When fetching venues, stores results in local data source`() {
        val venues = listOf(TestUtils.createVenue())
        val venuesResponse = VenuesResponse(venues)
        val venuesResponseWrapper = VenuesResponseWrapper(venuesResponse)
        whenever(remoteDataSource.fetchVenues()).thenReturn(Single.just(venuesResponseWrapper))
        whenever(localDataSource.storeVenues(venues)).thenReturn(Completable.complete())

        useCase().test().assertComplete()

        verify(localDataSource).storeVenues(venues)
    }

    @Test
    fun `When fetching venues with error, notify complete and does not store venues`() {
        whenever(remoteDataSource.fetchVenues()).thenReturn(Single.error(Throwable()))

        useCase().test().assertComplete()

        verify(localDataSource, never()).storeVenues(any())
    }
}