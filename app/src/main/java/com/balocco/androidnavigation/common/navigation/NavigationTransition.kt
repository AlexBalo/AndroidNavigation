package com.balocco.androidnavigation.common.navigation

import androidx.annotation.AnimRes

abstract class NavigationTransition {

    @AnimRes abstract fun enter(): Int

    @AnimRes abstract fun exit(): Int

    @AnimRes abstract fun popEnter(): Int

    @AnimRes abstract fun popExit(): Int

}