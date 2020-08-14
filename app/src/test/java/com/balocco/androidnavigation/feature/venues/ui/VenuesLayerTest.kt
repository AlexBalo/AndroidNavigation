package com.balocco.androidnavigation.feature.venues.ui

import com.balocco.androidnavigation.TestUtils
import com.balocco.androidnavigation.map.MapVenuesDisplayer
import com.balocco.androidnavigation.map.Marker
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class VenuesLayerTest {

    @Captor private lateinit var markersCaptor: ArgumentCaptor<List<Marker>>
    @Mock private lateinit var mapVenuesDisplayer: MapVenuesDisplayer

    private lateinit var layer: VenuesLayer

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)

        layer = VenuesLayer(
            mapVenuesDisplayer
        )
    }

    @Test fun `When new venues available, clear venues`() {
        val venue = TestUtils.createVenue("1")
        val venues = listOf(venue)

        layer.newVenuesAvailable(venues)

        verify(mapVenuesDisplayer).clearVenues()
    }

    @Test fun `When new venues available, shows markers for venues`() {
        val venue = TestUtils.createVenue("1")
        val venues = listOf(venue)

        layer.newVenuesAvailable(venues)

        verify(mapVenuesDisplayer).showMarkers(capture(markersCaptor))
        val markers = markersCaptor.value
        assertEquals(1, markers.size)
        assertEquals(venue, markers[0].tag())
        assertEquals(venue.name, markers[0].title())
    }

}