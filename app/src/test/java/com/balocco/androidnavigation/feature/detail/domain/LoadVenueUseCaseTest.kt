package com.balocco.androidnavigation.feature.detail.domain

import com.balocco.androidnavigation.TestUtils
import com.balocco.androidnavigation.data.local.VenuesLocalDataSource
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoadVenueUseCaseTest {

    @Mock lateinit var localDataSource: VenuesLocalDataSource

    private lateinit var useCase: LoadVenueUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        useCase = LoadVenueUseCase(localDataSource)
    }

    @Test
    fun `When fetching venue with id, returns venue from local storage`() {
        val id = "123"
        val venue = TestUtils.createVenue("123")
        whenever(localDataSource.loadVenue(id)).thenReturn(Single.just(venue))

        useCase(id).test().assertValue(venue)

        verify(localDataSource).loadVenue(id)
    }

}