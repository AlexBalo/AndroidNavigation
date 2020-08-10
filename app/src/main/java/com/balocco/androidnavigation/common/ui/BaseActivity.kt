package com.balocco.androidnavigation.common.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.balocco.androidcomponents.di.AppComponent
import com.balocco.androidnavigation.AndroidNavigationApplication

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        onInject((applicationContext as AndroidNavigationApplication).appComponent)
        super.onCreate(savedInstanceState)
    }

    abstract fun onInject(appComponent: AppComponent)
}