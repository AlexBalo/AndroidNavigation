package com.balocco.androidnavigation.feature.detail.domain

import com.balocco.androidnavigation.TestUtils
import com.balocco.androidnavigation.data.remote.RemoteDataSource
import com.balocco.androidnavigation.data.remote.response.VenueResponse
import com.balocco.androidnavigation.data.remote.response.VenueResponseWrapper
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FetchVenueDetailUseCaseTest {

    @Mock lateinit var remoteDataSource: RemoteDataSource

    private lateinit var useCase: FetchVenueDetailUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        useCase = FetchVenueDetailUseCase(remoteDataSource)
    }

    @Test
    fun `When fetching venue details, returns received venue`() {
        val venue = TestUtils.createVenue("1")
        val venueResponse = VenueResponse(venue)
        val venueResponseWrapper = VenueResponseWrapper(venueResponse)
        whenever(remoteDataSource.fetchVenueDetail("1"))
            .thenReturn(Single.just(venueResponseWrapper))

        useCase("1").test().assertValue(venue)
    }

}