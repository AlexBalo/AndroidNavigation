package com.balocco.androidnavigation.feature.map.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.balocco.androidnavigation.TestUtils
import com.balocco.androidnavigation.common.scheduler.TestSchedulerProvider
import com.balocco.androidnavigation.data.local.UserLocationLocalDataSource
import com.balocco.androidnavigation.data.model.Location
import com.balocco.androidnavigation.feature.map.domain.FetchVenuesUseCase
import com.balocco.androidnavigation.feature.map.domain.LocationPermissionGrantedUseCase
import com.balocco.androidnavigation.feature.map.domain.NearbyVenuesProvider
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Observable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertTrue

class MapsViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MapsViewModel

    @Captor private lateinit var userLocationCaptor: ArgumentCaptor<UserLocationState>
    @Captor private lateinit var venuesCaptor: ArgumentCaptor<NearbyVenuesState>
    @Mock private lateinit var userLocationObserver: Observer<UserLocationState>
    @Mock private lateinit var venuesObserver: Observer<NearbyVenuesState>
    @Mock private lateinit var fetchVenuesUseCase: FetchVenuesUseCase
    @Mock private lateinit var nearbyVenuesProvider: NearbyVenuesProvider
    @Mock private lateinit var userLocationLocalDataSource: UserLocationLocalDataSource
    @Mock private lateinit var locationPermissionGrantedUseCase: LocationPermissionGrantedUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel =
            MapsViewModel(
                TestSchedulerProvider(),
                userLocationLocalDataSource,
                nearbyVenuesProvider,
                fetchVenuesUseCase,
                locationPermissionGrantedUseCase
            )
        viewModel.userLocationState().observeForever(userLocationObserver)
        viewModel.nearbyVenuesState().observeForever(venuesObserver)
    }

    @Test
    fun `When map ready permission not granted, notifies permission required`() {
        whenever(nearbyVenuesProvider.venuesObservable()).thenReturn(Observable.just(emptyList()))
        whenever(locationPermissionGrantedUseCase()).thenReturn(false)

        viewModel.onMapReady()

        verify(userLocationObserver).onChanged(userLocationCaptor.capture())
        val state = userLocationCaptor.value
        assertEquals(UserLocationState.State.PERMISSION_REQUIRED, state.state)
        assertNull(state.userLocation)
    }

    @Test
    fun `When map ready permission granted and location available, notifies with location`() {
        val location = Location(90.0, 130.0)
        whenever(nearbyVenuesProvider.venuesObservable()).thenReturn(Observable.just(emptyList()))
        whenever(locationPermissionGrantedUseCase()).thenReturn(true)
        whenever(userLocationLocalDataSource.locationObservable())
            .thenReturn(Observable.just(location))

        viewModel.onMapReady()

        verify(userLocationObserver).onChanged(userLocationCaptor.capture())
        val state = userLocationCaptor.value
        assertEquals(UserLocationState.State.LOCATION_AVAILABLE, state.state)
        assertEquals(state.userLocation, location)
    }

    @Test
    fun `When map ready permission granted and location null, notifies with null location`() {
        whenever(nearbyVenuesProvider.venuesObservable()).thenReturn(Observable.just(emptyList()))
        whenever(locationPermissionGrantedUseCase()).thenReturn(true)
        whenever(userLocationLocalDataSource.locationObservable())
            .thenReturn(Observable.error(Throwable()))

        viewModel.onMapReady()

        verify(userLocationObserver).onChanged(userLocationCaptor.capture())
        val state = userLocationCaptor.value
        assertEquals(UserLocationState.State.LOCATION_AVAILABLE, state.state)
        assertNull(state.userLocation)
    }

    @Test
    fun `When map ready nearby venues available, notifies list of venues`() {
        val venues = listOf(TestUtils.createVenue("1"), TestUtils.createVenue("2"))
        whenever(nearbyVenuesProvider.venuesObservable()).thenReturn(Observable.just(venues))
        whenever(locationPermissionGrantedUseCase()).thenReturn(true)
        whenever(userLocationLocalDataSource.locationObservable())
            .thenReturn(Observable.error(Throwable()))

        viewModel.onMapReady()

        verify(venuesObserver).onChanged(venuesCaptor.capture())
        val state = venuesCaptor.value
        assertEquals(NearbyVenuesState.State.SUCCESS, state.state)
        assertEquals(state.venues, venues)
    }

    @Test
    fun `When map ready nearby venues throw error, notifies with error`() {
        whenever(nearbyVenuesProvider.venuesObservable()).thenReturn(Observable.error(Throwable()))
        whenever(locationPermissionGrantedUseCase()).thenReturn(true)
        whenever(userLocationLocalDataSource.locationObservable())
            .thenReturn(Observable.error(Throwable()))

        viewModel.onMapReady()

        verify(venuesObserver).onChanged(venuesCaptor.capture())
        val state = venuesCaptor.value
        assertEquals(NearbyVenuesState.State.ERROR, state.state)
        assertTrue(state.venues.isEmpty())
    }

    @Test
    fun `When location permission result with denied, notifies location unavailable`() {
        viewModel.onLocationPermissionResult(false)

        verify(userLocationObserver).onChanged(userLocationCaptor.capture())
        val state = userLocationCaptor.value
        assertEquals(UserLocationState.State.LOCATION_UNAVAILABLE, state.state)
        assertNull(state.userLocation)
    }

    @Test
    fun `When location permission result with granted, fetches location and returns it`() {
        val location = Location(90.0, 130.0)
        whenever(locationPermissionGrantedUseCase()).thenReturn(true)
        whenever(userLocationLocalDataSource.locationObservable()).thenReturn(
            Observable.just(
                location
            )
        )

        viewModel.onLocationPermissionResult(true)

        verify(userLocationObserver).onChanged(userLocationCaptor.capture())
        val state = userLocationCaptor.value
        assertEquals(UserLocationState.State.LOCATION_AVAILABLE, state.state)
        assertEquals(state.userLocation, location)
    }
}