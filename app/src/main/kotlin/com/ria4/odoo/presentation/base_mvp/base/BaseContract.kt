package com.ria4.odoo.presentation.base_mvp.base

import io.armcha.arch.BaseMVPContract

/**
 * Created by glovebx on 11.11.2019.
 */
interface BaseContract {

    interface View : BaseMVPContract.View

    interface Presenter<V : BaseMVPContract.View> : BaseMVPContract.Presenter<V>
}