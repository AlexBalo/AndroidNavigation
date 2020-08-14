package com.balocco.androidnavigation.feature.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.balocco.androidnavigation.R
import com.balocco.androidnavigation.common.ui.InjectableFragment
import com.balocco.androidnavigation.common.viewmodel.ViewModelFactory
import com.balocco.androidnavigation.feature.detail.viewmodel.DetailState
import com.balocco.androidnavigation.feature.detail.viewmodel.DetailViewModel
import com.balocco.androidnavigation.feature.detail.viewmodel.VenueDetail
import com.balocco.androidnavigation.feature.map.ui.MapsActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import javax.inject.Inject

private const val TAG = "DetailFragment"
private const val VENUE_ID_KEY = "VENUE_ID_KEY"

class DetailFragment() : InjectableFragment<MapsActivity>() {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var addressText: TextView
    private lateinit var categoriesText: TextView

    private lateinit var viewModel: DetailViewModel

    override fun getFragmentTag(): String = TAG

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail, container, false)

        collapsingToolbar = root.findViewById(R.id.collapsing_toolbar)
        addressText = root.findViewById(R.id.address)
        categoriesText = root.findViewById(R.id.categories)

        (activity as AppCompatActivity).setSupportActionBar(root.findViewById(R.id.toolbar))
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        collapsingToolbar.setContentScrimColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorPrimary
            )
        )
        collapsingToolbar.title = " "

        val venueId = arguments?.getString(VENUE_ID_KEY) ?: ""
        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(DetailViewModel::class.java)
        viewModel.detailState()
            .observe(requireActivity(), Observer { state -> handleDetailState(state) })
        viewModel.start(venueId)
        return root
    }

    private fun handleDetailState(state: DetailState) {
        when (state.state) {
            DetailState.State.ERROR -> { /* handle error, potentially going back */
            }
            DetailState.State.SUCCESS -> populateVenueView(state.venue)
        }
    }

    private fun populateVenueView(venueDetail: VenueDetail?) {
        venueDetail?.let { detail ->
            collapsingToolbar.title = detail.name
            addressText.text = detail.address
            categoriesText.text = detail.categories
        }
    }

    override fun onInject(activity: MapsActivity) {
        activity.component.detailComponent().create().inject(this)
    }

    companion object {
        fun newInstance(venueId: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString(VENUE_ID_KEY, venueId)
            fragment.arguments = args
            return fragment
        }
    }
}