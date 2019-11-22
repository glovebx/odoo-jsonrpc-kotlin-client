package com.ria4.odoo.presentation.screen.dispatch

import androidx.annotation.CallSuper
import com.ria4.odoo.App
import com.ria4.odoo.di.scope.PerActivity
import com.ria4.odoo.domain.interactor.UserInteractor
import com.ria4.odoo.presentation.base_mvp.api.ApiPresenter
import javax.inject.Inject

/**
 * Created by glovebx on 11.08.2017.
 */
@PerActivity
class DispatchPresenter @Inject constructor(private val userInteractor: UserInteractor)
    : ApiPresenter<DispatchContract.View>(), DispatchContract.Presenter {

    override fun onPresenterCreate() {
        super.onPresenterCreate()

        view.taskGuard()

        fetch(userInteractor.loadCurrentUser()) {
            // 赋值给Application
            App.instance.user = it
            // 进入主界面
            view?.openHomeActivity()
        }
    }

    override fun onRequestError(errorMessage: String?) {
        errorMessage?.run {
            // TODO: 要修改
            if (this == "The callable returned a null value") {
                view?.openLoginActivity()
                return
            }
        }
        // 报错！！
        view?.run {
            showError(errorMessage)
        }
    }
}