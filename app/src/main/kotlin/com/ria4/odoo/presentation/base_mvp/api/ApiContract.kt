package com.ria4.odoo.presentation.base_mvp.api

import com.ria4.odoo.presentation.base_mvp.base.BaseContract

/**
 * Created by glovebx on 11.11.2019.
 */
interface ApiContract {

    interface View : BaseContract.View {

        fun showLoading() {}

        fun hideLoading() {}

        fun showError(message: String?) {}
    }

    interface Presenter<V : BaseContract.View> : BaseContract.Presenter<V>
}