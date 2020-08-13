package com.balocco.androidnavigation.feature.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.balocco.androidnavigation.R
import com.balocco.androidnavigation.common.ui.BaseFragment

private const val TAG = "DetailFragment"

class DetailFragment() : BaseFragment() {

    override fun getFragmentTag(): String = TAG

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }
}