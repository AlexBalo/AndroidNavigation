package com.balocco.androidnavigation.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class LocationTest {

    @Test fun `When literal representation of location, return latitude and longitude string`() {
        val location = Location(12.0, 13.0)

        assertEquals("12.0,13.0", location.literal())
    }
}