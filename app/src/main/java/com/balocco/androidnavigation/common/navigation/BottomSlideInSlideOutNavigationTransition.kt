package com.balocco.androidnavigation.common.navigation

import androidx.annotation.AnimRes
import com.balocco.androidnavigation.R

class BottomSlideInSlideOutNavigationTransition() : NavigationTransition() {

    @AnimRes override fun enter(): Int = R.anim.enter_from_bottom

    @AnimRes override fun exit(): Int = R.anim.exit_from_bottom

    @AnimRes override fun popEnter(): Int = R.anim.enter_from_bottom

    @AnimRes override fun popExit(): Int = R.anim.exit_from_bottom

}