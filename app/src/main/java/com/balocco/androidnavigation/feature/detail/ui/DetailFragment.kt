package com.balocco.androidnavigation.feature.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.balocco.androidnavigation.R
import com.balocco.androidnavigation.common.navigation.Navigator
import com.balocco.androidnavigation.common.ui.InjectableFragment
import com.balocco.androidnavigation.common.viewmodel.ViewModelFactory
import com.balocco.androidnavigation.data.remote.ImageLoader
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
    @Inject lateinit var imageLoader: ImageLoader
    @Inject lateinit var navigator: Navigator

    private lateinit var backdropImage: ImageView
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var addressText: TextView
    private lateinit var categoriesText: TextView
    private lateinit var ratingText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var websiteText: TextView

    private lateinit var viewModel: DetailViewModel

    override fun getFragmentTag(): String = TAG

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail, container, false)

        backdropImage = root.findViewById(R.id.backdrop)
        collapsingToolbar = root.findViewById(R.id.collapsing_toolbar)
        addressText = root.findViewById(R.id.address)
        categoriesText = root.findViewById(R.id.categories)
        ratingText = root.findViewById(R.id.rating)
        descriptionText = root.findViewById(R.id.description)
        websiteText = root.findViewById(R.id.website)

        (activity as AppCompatActivity).setSupportActionBar(root.findViewById(R.id.toolbar))
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)
        setHasOptionsMenu(true)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> navigator.back()
        }
        return super.onOptionsItemSelected(item)
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
            categoriesText.text =
                if (detail.categories.isEmpty()) getString(R.string.detail_default) else detail.categories
            ratingText.text =
                if (detail.rating.isEmpty()) getString(R.string.detail_default) else detail.rating
            descriptionText.text =
                if (detail.description.isEmpty()) getString(R.string.detail_default) else detail.description
            websiteText.text =
                if (detail.website.isEmpty()) getString(R.string.detail_default) else detail.website
            imageLoader.loadImage(backdropImage, detail.photoUrl)
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