package com.balocco.androidnavigation.common.ui

import android.app.Activity

abstract class InjectableFragment<T : Activity> : BaseFragment() {

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        onInject(activity as T)
    }

    abstract fun onInject(activity: T)

}