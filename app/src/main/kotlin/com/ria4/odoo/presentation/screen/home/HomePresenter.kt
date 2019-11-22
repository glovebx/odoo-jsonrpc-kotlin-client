package com.ria4.odoo.presentation.screen.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.ria4.odoo.App
import com.ria4.odoo.di.scope.PerActivity
import com.ria4.odoo.domain.entity.User
import com.ria4.odoo.domain.interactor.UserInteractor
import com.ria4.odoo.presentation.base_mvp.api.ApiPresenter
import com.ria4.odoo.presentation.base_mvp.base.BaseFragment
import com.ria4.odoo.presentation.navigation.NavigationState
import com.ria4.odoo.presentation.utils.extensions.emptyString
import com.ria4.odoo.presentation.widget.navigation_view.NavigationId
import javax.inject.Inject

/**
 * Created by glovebx on 11.11.2019.
 */
@PerActivity
class HomePresenter @Inject constructor(private val userInteractor: UserInteractor)
    : ApiPresenter<HomeContract.View>(), HomeContract.Presenter {

    private var isArcIcon = false
    private var user: User? = null
    private var isDrawerOpened = false
    private var activeTitle = emptyString
    private var state: NavigationState? = null
    private var currentNavigationSelectedItem = 0

    @OnLifecycleEvent(value = Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        if (isArcIcon || isDrawerOpened) {
            view?.setArcArrowState()
        } else {
            view?.setArcHamburgerIconState()
        }
        view?.setToolBarTitle(activeTitle)
        user?.let {
            view?.updateDrawerInfo(it)
        }
    }

    override fun onPresenterCreate() {
        super.onPresenterCreate()
//        fetch(userInteractor.getAuthenticatedUser()) {
//            user = it
//            view?.updateDrawerInfo(it)
//        }
        user = App.instance.user
//        view?.updateDrawerInfo(user!!)
        view?.openHomeFragment()
    }

    override fun onPresenterDestroy() {
        super.onPresenterDestroy()
        userInteractor.clearCache()
    }

    override fun handleFragmentChanges(currentTag: String, fragment: Fragment) {
        val tag = if (fragment is BaseFragment<*, *>) {
            fragment.getTitle()
        } else {
            emptyString
        }

        view?.setToolBarTitle(tag)
        activeTitle = tag
        if (isArcIcon) {
            isArcIcon = false
            view?.setArcHamburgerIconState()
        }

        val checkPosition = when (tag) {
            NavigationId.HOME.name -> 0
            NavigationId.ABOUT.name -> 1
            else -> currentNavigationSelectedItem
        }

        if (currentNavigationSelectedItem != checkPosition) {
            currentNavigationSelectedItem = checkPosition
            view?.checkNavigationItem(currentNavigationSelectedItem)
        }
    }

    override fun handleDrawerOpen() {
        if (!isArcIcon)
            view?.setArcArrowState()
        isDrawerOpened = true
    }

    override fun handleDrawerClose() {
        if (!isArcIcon && isDrawerOpened)
            view?.setArcHamburgerIconState()
        isDrawerOpened = false
    }

    override fun logOut() {
        userInteractor.logOut()
        view?.openLoginActivity()
    }

    override fun saveNavigatorState(state: NavigationState?) {
        this.state = state
    }

    override fun getNavigatorState(): NavigationState? {
        return state
    }
}