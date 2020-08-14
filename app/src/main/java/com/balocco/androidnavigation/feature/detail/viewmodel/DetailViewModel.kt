package com.balocco.androidnavigation.feature.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.balocco.androidcomponents.common.scheduler.SchedulerProvider
import com.balocco.androidnavigation.common.viewmodel.BaseViewModel
import com.balocco.androidnavigation.data.model.Venue
import com.balocco.androidnavigation.feature.detail.domain.FetchVenueDetailUseCase
import com.balocco.androidnavigation.feature.detail.domain.LoadVenueUseCase
import com.balocco.androidnavigation.feature.detail.domain.VenueDetailMapper
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val venueDetailMapper: VenueDetailMapper,
    private val loadVenueUseCase: LoadVenueUseCase,
    private val fetchVenueDetailUseCase: FetchVenueDetailUseCase
) : BaseViewModel() {

    private var detailState: MutableLiveData<DetailState> = MutableLiveData()
    private var venueDetail = VenueDetail()

    fun detailState(): LiveData<DetailState> = detailState

    fun start(venueId: String) {
        venueDetail.id = venueId
        loadVenueUseCase(venueId)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { venue -> handleLocalVenueResult(venue) },
                { detailState.value = DetailState(DetailState.State.ERROR, venueDetail) }
            )
            .addTo(compositeDisposable)

        // Ideally we only want to listen to local source and update it from data coming
        // remotely but for this use case we directly update UI from remote result
        fetchVenueDetailUseCase(venueId)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { venue -> handleLocalVenueResult(venue) },
                { detailState.value = DetailState(DetailState.State.ERROR, venueDetail) }
            )
            .addTo(compositeDisposable)
    }

    private fun handleLocalVenueResult(venue: Venue) {
        venueDetail = venueDetailMapper.mapFromVenue(venueDetail, venue)
        detailState.value = DetailState(DetailState.State.SUCCESS, venueDetail)
    }
}