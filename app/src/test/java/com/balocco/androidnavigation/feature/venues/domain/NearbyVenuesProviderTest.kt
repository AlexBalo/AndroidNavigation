package com.balocco.androidnavigation.feature.venues.domain

import com.balocco.androidnavigation.TestUtils
import com.balocco.androidnavigation.data.local.VenuesLocalDataSource
import com.balocco.androidnavigation.data.model.Location
import com.balocco.androidnavigation.data.model.Venue
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val RADIUS = 10.0
private val CENTER = Location(12.0, 13.0)

class NearbyVenuesProviderTest {

    @Mock private lateinit var venuesLocalDataSource: VenuesLocalDataSource
    @Mock private lateinit var venueWithinRadiusUseCase: VenueWithinRadiusUseCase

    private lateinit var nearbyVenuesProvider: NearbyVenuesProvider

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)

        nearbyVenuesProvider =
            NearbyVenuesProvider(
                venuesLocalDataSource,
                venueWithinRadiusUseCase
            )
    }

    @Test fun `When venues from local storage available, returns only venues in range`() {
        val mapAlreadyInStorage = HashMap<String, Venue>()
        val venue1 = TestUtils.createVenue("1")
        val venue2 = TestUtils.createVenue("2")
        mapAlreadyInStorage["1"] = venue1
        mapAlreadyInStorage["2"] = venue2
        whenever(venuesLocalDataSource.venuesStorageObservable())
            .thenReturn(Observable.just(HashMap()))
        whenever(venuesLocalDataSource.loadVenues()).thenReturn(mapAlreadyInStorage)
        whenever(
            venueWithinRadiusUseCase(
                venue1,
                CENTER,
                RADIUS
            )
        ).thenReturn(true)
        whenever(
            venueWithinRadiusUseCase(
                venue2,
                CENTER,
                RADIUS
            )
        ).thenReturn(false)
        val testObserver = nearbyVenuesProvider.venuesObservable().test()

        nearbyVenuesProvider.updateProximityInfo(
            CENTER,
            RADIUS
        )

        testObserver.assertValues(
            listOf(),
            listOf(venue1)
        )
    }

    @Test fun `When new venues available, returns venues in range`() {
        val newVenues = HashMap<String, Venue>()
        val venue1 = TestUtils.createVenue("1")
        val venue2 = TestUtils.createVenue("2")
        newVenues["1"] = venue1
        newVenues["2"] = venue2
        whenever(venuesLocalDataSource.venuesStorageObservable())
            .thenReturn(Observable.just(newVenues))
        whenever(venuesLocalDataSource.loadVenues()).thenReturn(HashMap())
        whenever(venueWithinRadiusUseCase(eq(venue1), any(), any())).thenReturn(true)
        whenever(venueWithinRadiusUseCase(eq(venue2), any(), any())).thenReturn(true)
        val testObserver = nearbyVenuesProvider.venuesObservable().test()

        nearbyVenuesProvider.updateProximityInfo(
            CENTER,
            RADIUS
        )

        testObserver.assertValues(
            listOf(venue1, venue2),
            listOf()
        )
    }
}