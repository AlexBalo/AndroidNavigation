package com.balocco.androidnavigation.map

import com.balocco.androidnavigation.TestUtils
import com.balocco.androidnavigation.feature.venues.ui.VenueMarker
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MapVenuesDisplayerImplTest {

    @Mock lateinit var map: Map

    private lateinit var venuesDisplayer: MapVenuesDisplayer

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        venuesDisplayer = MapVenuesDisplayerImpl()
        venuesDisplayer.initWithMap(map)
    }

    @Test
    fun `When clear venues, clears venues from the map`() {
        venuesDisplayer.clearVenues()

        verify(map).clearVenues()
    }

    @Test
    fun `When show markers, shows markers on the map`() {
        val venue1 = TestUtils.createVenue("1")
        val marker =
            VenueMarker(venue1)
        val markers = listOf(marker)

        venuesDisplayer.showMarkers(markers)

        verify(map).showMarkers(markers)
    }
}