package com.balocco.androidnavigation.common.navigation

import com.balocco.androidnavigation.common.ui.BaseFragment

interface Navigator {

    fun navigate(
        fragment: BaseFragment,
        navigationTransition: NavigationTransition = BottomSlideInSlideOutNavigationTransition(),
        addToBackStack: Boolean = true
    )

    fun back()
}