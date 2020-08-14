package com.balocco.androidnavigation.feature.venues.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.balocco.androidnavigation.R
import com.balocco.androidnavigation.common.navigation.Navigator
import com.balocco.androidnavigation.common.ui.InjectableFragment
import com.balocco.androidnavigation.common.viewmodel.ViewModelFactory
import com.balocco.androidnavigation.data.model.Venue
import com.balocco.androidnavigation.feature.detail.ui.DetailFragment
import com.balocco.androidnavigation.feature.map.ui.MapsActivity
import com.balocco.androidnavigation.feature.venues.viewmodel.NearbyVenuesState
import com.balocco.androidnavigation.feature.venues.viewmodel.VenuesViewModel
import com.balocco.androidnavigation.map.MapInfoProvider
import com.balocco.androidnavigation.map.MapInteractor
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

private const val TAG = "DetailFragment"

class VenuesFragment() : InjectableFragment<MapsActivity>() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var navigator: Navigator
    @Inject lateinit var mapInteractor: MapInteractor
    @Inject lateinit var mapInfoProvider: MapInfoProvider
    @Inject lateinit var venuesLayer: VenuesLayer

    private lateinit var viewModel: VenuesViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun getFragmentTag(): String = TAG

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_venues, container, false)

        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(VenuesViewModel::class.java)
        viewModel.nearbyVenuesState()
            .observe(requireActivity(), Observer { state -> handleNearbyVenuesState(state) })
        viewModel.start()

        subscribeToMapEvents()

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    override fun onInject(activity: MapsActivity) {
        activity.component.venuesComponent().create().inject(this)
    }

    private fun subscribeToMapEvents() {
        mapInteractor.mapIdleObservable().subscribe {
            viewModel.onMapCenterChanged(
                mapInfoProvider.mapCenter(),
                mapInfoProvider.mapVisibleRadiusInMeters()
            )
        }.addTo(compositeDisposable)

        mapInteractor.mapMarkerClickedObservable().subscribe { marker ->
            venuesLayer.markerClicked(marker)
        }.addTo(compositeDisposable)

        mapInteractor.mapMarkerInfoBubbleClickedObservable().subscribe { marker ->
            val venue = marker.tag() as Venue
            val detailFragment = DetailFragment.newInstance(venueId = venue.id)
            navigator.navigate(fragment = detailFragment)
        }.addTo(compositeDisposable)
    }

    private fun handleNearbyVenuesState(nearbyVenuesState: NearbyVenuesState) {
        when (nearbyVenuesState.state) {
            NearbyVenuesState.State.SUCCESS -> {
                venuesLayer.newVenuesAvailable(nearbyVenuesState.venues)
            }
            NearbyVenuesState.State.ERROR -> {
                venuesLayer.errorLoadingVenues()
            }
        }
    }

    companion object {
        fun newInstance(): VenuesFragment {
            return VenuesFragment()
        }
    }
}