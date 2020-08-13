package com.balocco.androidnavigation.common.navigation

import androidx.annotation.AnimRes

class NoNavigationTransition() : NavigationTransition() {

    @AnimRes override fun enter(): Int = 0

    @AnimRes override fun exit(): Int = 0

    @AnimRes override fun popEnter(): Int = 0

    @AnimRes override fun popExit(): Int = 0

}