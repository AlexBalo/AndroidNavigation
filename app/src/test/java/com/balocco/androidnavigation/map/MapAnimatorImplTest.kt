package com.balocco.androidnavigation.map

import com.balocco.androidnavigation.data.model.Location
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MapAnimatorImplTest {

    @Mock lateinit var map: Map
    @Mock lateinit var location: Location

    private lateinit var animator: MapAnimator

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        animator = MapAnimatorImpl()
        animator.initWithMap(map)
    }

    @Test
    fun `When move to location with valid location, centers map to location with zoom`() {
        animator.centerTo(location)

        verify(map).centerTo(location, 15.0f)
    }

    @Test
    fun `When move to location with null location, does not centers map`() {
        animator.centerTo(null)

        verify(map, never()).centerTo(any(), any())
    }

}