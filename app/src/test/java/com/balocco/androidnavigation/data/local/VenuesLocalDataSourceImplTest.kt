package com.balocco.androidnavigation.data.local

import com.balocco.androidnavigation.TestUtils
import com.balocco.androidnavigation.data.model.Venue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VenuesLocalDataSourceImplTest {

    private lateinit var venuesLocalDataSource: VenuesLocalDataSource

    @Before fun setUp() {
        venuesLocalDataSource = VenuesLocalDataSourceImpl()
    }

    @Test fun `When loading venue with id, returns locally stored venue`() {
        val venue = TestUtils.createVenue("1")
        val venues = listOf(venue)
        venuesLocalDataSource.storeVenues(venues).test()

        venuesLocalDataSource.loadVenue("1").test().assertValue(venue)
    }

    @Test fun `When storing venues, observer is notified with stored venues`() {
        val venue1 = TestUtils.createVenue("1")
        val venue2 = TestUtils.createVenue("2")
        val venues = listOf(venue1, venue2)
        val testObserver = venuesLocalDataSource.venuesObservable().test()

        venuesLocalDataSource.storeVenues(venues).test()

        val expectedMap = HashMap<String, Venue>()
        expectedMap["1"] = venue1
        expectedMap["2"] = venue2
        testObserver.assertValues(expectedMap)
    }

    @Test fun `When storing venues and fetching them back, returns map of venues`() {
        val venue1 = TestUtils.createVenue("1")
        val venue2 = TestUtils.createVenue("2")
        val venues = listOf(venue1, venue2)

        venuesLocalDataSource.storeVenues(venues).test()

        val expectedMap = HashMap<String, Venue>()
        expectedMap["1"] = venue1
        expectedMap["2"] = venue2
        assertEquals(expectedMap, venuesLocalDataSource.loadVenues())
    }
}