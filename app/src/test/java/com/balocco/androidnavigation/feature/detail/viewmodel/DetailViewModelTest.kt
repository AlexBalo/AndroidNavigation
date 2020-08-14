package com.balocco.androidnavigation.feature.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.balocco.androidnavigation.TestUtils
import com.balocco.androidnavigation.common.scheduler.TestSchedulerProvider
import com.balocco.androidnavigation.feature.detail.domain.LoadVenueUseCase
import com.balocco.androidnavigation.feature.venues.domain.VenueDetailMapper
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
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


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel =
            DetailViewModel(
                TestSchedulerProvider(),
                venueDetailMapper,
                loadVenueUseCase
            )
        viewModel.detailState().observeForever(venueDetailObserver)
    }

    @Test
    fun `When started error fetching venue, notifies detail with error`() {
        whenever(loadVenueUseCase(VENUE_ID)).thenReturn(Single.error(Throwable()))

        viewModel.start(VENUE_ID)

        verify(venueDetailObserver).onChanged(detailCaptor.capture())
        val state = detailCaptor.value
        assertEquals(DetailState.State.ERROR, state.state)
    }

    @Test
    fun `When started successfully fetching venue, notifies detail with success and venue detail`() {
        val venue = TestUtils.createVenue("1")
        val venueDetail = VenueDetail()
        whenever(loadVenueUseCase(VENUE_ID)).thenReturn(Single.just(venue))
        whenever(venueDetailMapper.mapFromVenue(any(), eq(venue))).thenReturn(venueDetail)

        viewModel.start(VENUE_ID)

        verify(venueDetailObserver).onChanged(detailCaptor.capture())
        val state = detailCaptor.value
        assertEquals(DetailState.State.SUCCESS, state.state)
        assertEquals(venueDetail, state.venue)
    }
}