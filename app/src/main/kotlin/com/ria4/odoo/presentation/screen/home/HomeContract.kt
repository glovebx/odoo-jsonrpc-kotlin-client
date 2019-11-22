package com.ria4.odoo.presentation.screen.home

import androidx.fragment.app.Fragment
import com.ria4.odoo.domain.entity.User
import com.ria4.odoo.presentation.base_mvp.base.BaseContract
import com.ria4.odoo.presentation.navigation.NavigationState

/**
 * Created by glovebx on 11.11.2019.
 */
interface HomeContract {

    interface View : BaseContract.View {

        fun openHomeFragment()

        fun openLoginActivity()

        fun setArcArrowState()

        fun setArcHamburgerIconState()

        fun setToolBarTitle(title: String)

        fun updateDrawerInfo(user: User)

        fun checkNavigationItem(position: Int)
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun logOut()

        fun saveNavigatorState(state: NavigationState?)

        fun getNavigatorState(): NavigationState?

        fun handleFragmentChanges(currentTag: String,fragment: Fragment)

        fun handleDrawerOpen()

        fun handleDrawerClose()
    }
}