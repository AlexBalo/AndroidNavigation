package com.balocco.androidnavigation.common.navigation

import androidx.fragment.app.FragmentActivity
import com.balocco.androidnavigation.R
import com.balocco.androidnavigation.common.ui.BaseFragment
import javax.inject.Inject

class MainNavigator @Inject constructor(
    private val activity: FragmentActivity
) : Navigator {

    override fun navigate(
        fragment: BaseFragment,
        navigationTransition: NavigationTransition,
        addToBackStack: Boolean
    ) {
        val fragmentManager = activity.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            navigationTransition.enter(),
            navigationTransition.exit(),
            navigationTransition.popEnter(),
            navigationTransition.popExit()
        )
        fragmentTransaction.replace(R.id.overlay, fragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getFragmentTag())
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun back() {
        val fragmentManager = activity.supportFragmentManager
        fragmentManager.popBackStack()
    }
}