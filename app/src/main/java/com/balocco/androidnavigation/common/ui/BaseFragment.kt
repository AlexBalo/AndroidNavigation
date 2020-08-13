package com.balocco.androidnavigation.common.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract fun getFragmentTag(): String

}