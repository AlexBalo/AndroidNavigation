package com.balocco.androidnavigation.map

import com.balocco.androidnavigation.data.model.Location
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UserLocationLayerTest {

    @Mock private lateinit var mapInfoProvider: MapInfoProvider
    @Mock private lateinit var mapAnimator: MapAnimator

    private lateinit var layer: UserLocationLayer

    @Before fun setUp() {
        MockitoAnnotations.initMocks(this)

        layer =
            UserLocationLayer(
                mapInfoProvider,
                mapAnimator
            )
    }

    @Test fun `When location available, enables user location`() {
        layer.locationAvailable(Location(latitude = 12.0, longitude = 13.0))

        verify(mapInfoProvider).setUserLocationEnabled(true)
    }

    @Test fun `When location available, moves camera to location`() {
        val location = Location(latitude = 12.0, longitude = 13.0)

        layer.locationAvailable(location)

        verify(mapAnimator).centerTo(location)
    }

}