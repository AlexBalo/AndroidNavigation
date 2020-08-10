package com.balocco.androidnavigation.feature.map.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.balocco.androidnavigation.common.scheduler.TestSchedulerProvider
import com.balocco.androidnavigation.data.local.UserLocationDataSource
import com.balocco.androidnavigation.data.model.Location
import com.balocco.androidnavigation.feature.map.domain.LocationPermissionGrantedUseCase
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

class MapsViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MapsViewModel

    @Captor private lateinit var userLocationCaptor: ArgumentCaptor<UserLocationState>
    @Mock private lateinit var observer: Observer<UserLocationState>
    @Mock private lateinit var userLocationDataSource: UserLocationDataSource
    @Mock private lateinit var locationPermissionGrantedUseCase: LocationPermissionGrantedUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel =
            MapsViewModel(
                TestSchedulerProvider(),
                userLocationDataSource,
                locationPermissionGrantedUseCase
            )
        viewModel.userLocationState().observeForever(observer)
    }

    @Test
    fun `When map ready permission not granted, notifies permission required`() {
        whenever(locationPermissionGrantedUseCase()).thenReturn(false)

        viewModel.onMapReady()

        verify(observer).onChanged(userLocationCaptor.capture())
        val state = userLocationCaptor.value
        assertEquals(UserLocationState.State.PERMISSION_REQUIRED, state.state)
        assertNull(state.userLocation)
    }

    @Test
    fun `When map ready permission granted and location available, notifies with location`() {
        val location = Location(90.0, 130.0)
        whenever(locationPermissionGrantedUseCase()).thenReturn(true)
        whenever(userLocationDataSource.locationObservable()).thenReturn(Observable.just(location))

        viewModel.onMapReady()

        verify(observer).onChanged(userLocationCaptor.capture())
        val state = userLocationCaptor.value
        assertEquals(UserLocationState.State.LOCATION_AVAILABLE, state.state)
        assertEquals(state.userLocation, location)
    }

    @Test
    fun `When map ready permission granted and location null, notifies with null location`() {
        whenever(locationPermissionGrantedUseCase()).thenReturn(true)
        whenever(userLocationDataSource.locationObservable()).thenReturn(Observable.error(Throwable()))

        viewModel.onMapReady()

        verify(observer).onChanged(userLocationCaptor.capture())
        val state = userLocationCaptor.value
        assertEquals(UserLocationState.State.LOCATION_AVAILABLE, state.state)
        assertNull(state.userLocation)
    }

    @Test
    fun `When location permission result with denied, notifies location unavailable`() {
        viewModel.onLocationPermissionResult(false)

        verify(observer).onChanged(userLocationCaptor.capture())
        val state = userLocationCaptor.value
        assertEquals(UserLocationState.State.LOCATION_UNAVAILABLE, state.state)
        assertNull(state.userLocation)
    }

    @Test
    fun `When location permission result with granted, fetches location and returns it`() {
        val location = Location(90.0, 130.0)
        whenever(locationPermissionGrantedUseCase()).thenReturn(true)
        whenever(userLocationDataSource.locationObservable()).thenReturn(Observable.just(location))

        viewModel.onLocationPermissionResult(true)

        verify(observer).onChanged(userLocationCaptor.capture())
        val state = userLocationCaptor.value
        assertEquals(UserLocationState.State.LOCATION_AVAILABLE, state.state)
        assertEquals(state.userLocation, location)
    }
}