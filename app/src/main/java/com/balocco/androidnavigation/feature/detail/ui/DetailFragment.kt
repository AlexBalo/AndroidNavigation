package com.balocco.androidnavigation.feature.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.balocco.androidnavigation.R
import com.balocco.androidnavigation.common.ui.InjectableFragment
import com.balocco.androidnavigation.common.viewmodel.ViewModelFactory
import com.balocco.androidnavigation.feature.detail.viewmodel.DetailViewModel
import com.balocco.androidnavigation.feature.map.ui.MapsActivity
import com.google.android.material.appbar.CollapsingToolbarLayout
import javax.inject.Inject

private const val TAG = "DetailFragment"
private const val VENUE_ID_KEY = "VENUE_ID_KEY"

class DetailFragment() : InjectableFragment<MapsActivity>() {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var collapsingToolbar: CollapsingToolbarLayout

    private lateinit var viewModel: DetailViewModel

    override fun getFragmentTag(): String = TAG

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail, container, false)

        collapsingToolbar = root.findViewById(R.id.collapsing_toolbar)

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

        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(DetailViewModel::class.java)

        return root
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