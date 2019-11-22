package com.ria4.odoo.presentation.base_mvp.base

import io.armcha.arch.BaseMVPPresenter

/**
 * Created by glovebx on 11.11.2019.
 */
abstract class BasePresenter<V : BaseContract.View> : BaseMVPPresenter<V>(), BaseContract.Presenter<V>