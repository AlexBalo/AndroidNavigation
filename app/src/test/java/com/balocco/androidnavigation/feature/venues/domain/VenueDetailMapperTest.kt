package com.balocco.androidnavigation.feature.venues.domain

import com.balocco.androidnavigation.TestUtils
import com.balocco.androidnavigation.feature.detail.viewmodel.VenueDetail
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class VenueDetailMapperTest {

    private lateinit var mapper: VenueDetailMapper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mapper = VenueDetailMapper()
    }

    @Test
    fun `When map from venue, returns venue detail with venue name`() {
        val venue = TestUtils.createVenue(
            id = "123",
            name = "VenueName"
        )

        val venueDetail = mapper.mapFromVenue(VenueDetail(), venue)

        assertEquals("VenueName", venueDetail.name)
    }

    @Test
    fun `When map from venue, returns venue detail with formatted address`() {
        val address = listOf("Address 1", "Address 2")
        val venue = TestUtils.createVenue(
            id = "123",
            name = "VenueName",
            formattedAddress = address
        )

        val venueDetail = mapper.mapFromVenue(VenueDetail(), venue)

        assertEquals("Address 1\nAddress 2", venueDetail.address)
    }

    @Test
    fun `When map from venue, returns venue detail with formatted categories`() {
        val categories = listOf(
            TestUtils.createVenueCategory("Category1"),
            TestUtils.createVenueCategory("Category2")
        )
        val venue = TestUtils.createVenue(
            id = "123",
            name = "VenueName",
            categories = categories
        )

        val venueDetail = mapper.mapFromVenue(VenueDetail(), venue)

        assertEquals("Category1, Category2", venueDetail.categories)
    }
}