package com.balocco.androidnavigation.feature.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.balocco.androidnavigation.TestUtils
import com.balocco.androidnavigation.common.scheduler.TestSchedulerProvider
import com.balocco.androidnavigation.feature.detail.domain.FetchVenueDetailUseCase
import com.balocco.androidnavigation.feature.detail.domain.LoadVenueUseCase
import com.balocco.androidnavigation.feature.detail.domain.VenueDetailMapper
import com.nhaarman.mockito_kotlin.*
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val VENUE_ID = "id"

class DetailViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailViewModel

    @Captor private lateinit var detailCaptor: ArgumentCaptor<DetailState>
    @Mock private lateinit var venueDetailObserver: Observer<DetailState>
    @Mock private lateinit var venueDetailMapper: VenueDetailMapper
    @Mock private lateinit var loadVenueUseCase: LoadVenueUseCase
    @Mock private lateinit var fetchVenueDetailUseCase: FetchVenueDetailUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel =
            DetailViewModel(
                TestSchedulerProvider(),
                venueDetailMapper,
                loadVenueUseCase,
                fetchVenueDetailUseCase
            )
        viewModel.detailState().observeForever(venueDetailObserver)
    }

    @Test
    fun `When started error loading venue, notifies detail with error`() {
        whenever(loadVenueUseCase(VENUE_ID)).thenReturn(Single.error(Throwable()))
        whenever(fetchVenueDetailUseCase(VENUE_ID)).thenReturn(Single.error(Throwable()))

        viewModel.start(VENUE_ID)

        // Called two times one locally and the second from remote
        verify(venueDetailObserver, times(2)).onChanged(detailCaptor.capture())
        val firstState = detailCaptor.allValues[0]
        assertEquals(DetailState.State.ERROR, firstState.state)
    }

    @Test
    fun `When started successfully loading venue, notifies detail with success and venue detail`() {
        val venue = TestUtils.createVenue("1")
        val venueDetail = VenueDetail()
        whenever(loadVenueUseCase(VENUE_ID)).thenReturn(Single.just(venue))
        whenever(fetchVenueDetailUseCase(VENUE_ID)).thenReturn(Single.just(venue))
        whenever(venueDetailMapper.mapFromVenue(any(), eq(venue))).thenReturn(venueDetail)

        viewModel.start(VENUE_ID)

        // Called two times one locally and the second from remote
        verify(venueDetailObserver, times(2)).onChanged(detailCaptor.capture())
        val firstState = detailCaptor.allValues[0]
        assertEquals(DetailState.State.SUCCESS, firstState.state)
        assertEquals(venueDetail, firstState.venue)
    }

    @Test
    fun `When started successfully fetching venue, notifies detail with success and venue detail`() {
        val venue = TestUtils.createVenue("1")
        val venueDetail = VenueDetail()
        whenever(loadVenueUseCase(VENUE_ID)).thenReturn(Single.just(venue))
        whenever(fetchVenueDetailUseCase(VENUE_ID)).thenReturn(Single.just(venue))
        whenever(venueDetailMapper.mapFromVenue(any(), eq(venue))).thenReturn(venueDetail)

        viewModel.start(VENUE_ID)

        // Called two times one locally and the second from remote
        verify(venueDetailObserver, times(2)).onChanged(detailCaptor.capture())
        val state = detailCaptor.value
        assertEquals(DetailState.State.SUCCESS, state.state)
        assertEquals(venueDetail, state.venue)
    }

    @Test
    fun `When started fetching venue throw error, notifies detail with error`() {
        val venue = TestUtils.createVenue("1")
        val venueDetail = VenueDetail()
        whenever(loadVenueUseCase(VENUE_ID)).thenReturn(Single.just(venue))
        whenever(fetchVenueDetailUseCase(VENUE_ID)).thenReturn(Single.error(Throwable()))
        whenever(venueDetailMapper.mapFromVenue(any(), eq(venue))).thenReturn(venueDetail)

        viewModel.start(VENUE_ID)

        // Called two times one locally and the second from remote
        verify(venueDetailObserver, times(2)).onChanged(detailCaptor.capture())
        val lastState = detailCaptor.value
        assertEquals(DetailState.State.ERROR, lastState.state)
        assertEquals(venueDetail, lastState.venue)
    }
}