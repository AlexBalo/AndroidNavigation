package com.balocco.androidnavigation.feature.detail.domain

import com.balocco.androidnavigation.TestUtils
import com.balocco.androidnavigation.data.model.VenuePhoto
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

        mapper =
            VenueDetailMapper()
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

    @Test
    fun `When map from venue, returns venue detail with rating`() {
        val venue = TestUtils.createVenue(
            id = "123",
            name = "VenueName",
            rating = 9.0
        )

        val venueDetail = mapper.mapFromVenue(VenueDetail(), venue)

        assertEquals("9.0", venueDetail.rating)
    }

    @Test
    fun `When map from venue, returns venue detail with description`() {
        val venue = TestUtils.createVenue(
            id = "123",
            name = "VenueName",
            description = "This is description"
        )

        val venueDetail = mapper.mapFromVenue(VenueDetail(), venue)

        assertEquals("This is description", venueDetail.description)
    }

    @Test
    fun `When map from venue, returns venue detail with website url`() {
        val venue = TestUtils.createVenue(
            id = "123",
            name = "VenueName",
            url = "http://www.example.com"
        )

        val venueDetail = mapper.mapFromVenue(VenueDetail(), venue)

        assertEquals("http://www.example.com", venueDetail.website)
    }

    @Test
    fun `When map from venue without photo, returns venue with empty photo url`() {
        val venue = TestUtils.createVenue(
            id = "123",
            name = "VenueName",
            url = "http://www.example.com"
        )

        val venueDetail = mapper.mapFromVenue(VenueDetail(), venue)

        assertEquals("", venueDetail.photoUrl)
    }

    @Test
    fun `When map from venue with photo, returns venue with photo url`() {
        val venuePhoto = VenuePhoto(
            prefix = "prefix/",
            suffix = "/suffix",
            width = 123,
            height = 123
        )
        val venue = TestUtils.createVenue(
            id = "123",
            name = "VenueName",
            url = "http://www.example.com",
            venuePhoto = venuePhoto
        )

        val venueDetail = mapper.mapFromVenue(VenueDetail(), venue)

        assertEquals("prefix/123x123/suffix", venueDetail.photoUrl)
    }
}