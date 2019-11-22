package com.ria4.odoo.presentation.screen.dispatch

import com.ria4.odoo.presentation.base_mvp.base.BaseContract

/**
 * Created by glovebx on 11.08.2017.
 */
interface DispatchContract {

    interface View : BaseContract.View {

        fun taskGuard()

        fun openHomeActivity()

        fun openLoginActivity()

        fun showError(message: String?)
    }

    interface Presenter : BaseContract.Presenter<View>
}