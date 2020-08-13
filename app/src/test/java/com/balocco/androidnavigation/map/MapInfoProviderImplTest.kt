package com.balocco.androidnavigation.map

import com.balocco.androidnavigation.data.model.Location
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MapInfoProviderImplTest {

    @Mock lateinit var map: Map
    @Mock lateinit var location: Location

    private lateinit var infoProvider: MapInfoProvider

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        infoProvider = MapInfoProviderImpl()
        infoProvider.initWithMap(map)
    }

    @Test
    fun `When set user location enabled, sets user location enabled on the map`() {
        infoProvider.setUserLocationEnabled(true)

        verify(map).setUserLocationEnabled(true)
    }

    @Test
    fun `When map center, gets map center from map`() {
        infoProvider.mapCenter()

        verify(map).center()
    }

    @Test
    fun `When map visible radius, returns map visible radius in meters`() {
        infoProvider.mapVisibleRadiusInMeters()

        verify(map).visibleRadiusInMeters()
    }

}