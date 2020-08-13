package com.balocco.androidnavigation.feature.venues.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.balocco.androidnavigation.TestUtils
import com.balocco.androidnavigation.common.scheduler.TestSchedulerProvider
import com.balocco.androidnavigation.data.local.UserLocationLocalDataSource
import com.balocco.androidnavigation.data.model.Location
import com.balocco.androidnavigation.feature.map.domain.LocationPermissionGrantedUseCase
import com.balocco.androidnavigation.feature.venues.domain.FetchRestaurantsUseCase
import com.balocco.androidnavigation.feature.venues.domain.NearbyVenuesProvider
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class VenuesViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: VenuesViewModel

    @Captor private lateinit var venuesCaptor: ArgumentCaptor<NearbyVenuesState>
    @Mock private lateinit var venuesObserver: Observer<NearbyVenuesState>
    @Mock private lateinit var fetchRestaurantsUseCase: FetchRestaurantsUseCase
    @Mock private lateinit var nearbyVenuesProvider: NearbyVenuesProvider
    @Mock private lateinit var userLocationLocalDataSource: UserLocationLocalDataSource
    @Mock private lateinit var locationPermissionGrantedUseCase: LocationPermissionGrantedUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel =
            VenuesViewModel(
                TestSchedulerProvider(),
                nearbyVenuesProvider,
                fetchRestaurantsUseCase
            )
        viewModel.nearbyVenuesState().observeForever(venuesObserver)
    }

    @Test
    fun `When started nearby venues available, notifies list of venues`() {
        val venues = listOf(TestUtils.createVenue("1"), TestUtils.createVenue("2"))
        whenever(nearbyVenuesProvider.venuesObservable()).thenReturn(Observable.just(venues))
        whenever(locationPermissionGrantedUseCase()).thenReturn(true)
        whenever(userLocationLocalDataSource.locationObservable())
            .thenReturn(Observable.error(Throwable()))

        viewModel.start()

        verify(venuesObserver).onChanged(venuesCaptor.capture())
        val state = venuesCaptor.value
        assertEquals(NearbyVenuesState.State.SUCCESS, state.state)
        assertEquals(state.venues, venues)
    }

    @Test
    fun `When started nearby venues throw error, notifies with error`() {
        whenever(nearbyVenuesProvider.venuesObservable()).thenReturn(Observable.error(Throwable()))
        whenever(locationPermissionGrantedUseCase()).thenReturn(true)
        whenever(userLocationLocalDataSource.locationObservable())
            .thenReturn(Observable.error(Throwable()))

        viewModel.start()

        verify(venuesObserver).onChanged(venuesCaptor.capture())
        val state = venuesCaptor.value
        assertEquals(NearbyVenuesState.State.ERROR, state.state)
        assertTrue(state.venues.isEmpty())
    }

    @Test
    fun `When map center changed, updates proximity information in venue provider`() {
        val center = Location(12.0, 13.0)
        val radius = 134.0
        whenever(
            fetchRestaurantsUseCase(
                center.literal(),
                radius
            )
        ).thenReturn(Completable.complete())

        viewModel.onMapCenterChanged(center, radius)

        verify(nearbyVenuesProvider).updateProximityInfo(center, radius)
    }

    @Test
    fun `When map center changed, fetches restaurant nearby location`() {
        val center = Location(12.0, 13.0)
        val radius = 134.0
        whenever(
            fetchRestaurantsUseCase(
                center.literal(),
                radius
            )
        ).thenReturn(Completable.complete())

        viewModel.onMapCenterChanged(center, radius)

        verify(fetchRestaurantsUseCase)(center.literal(), radius)
    }
}